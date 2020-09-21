package com.mtjin.nomoneytrip.data.reservation.source

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.nomoneytrip.api.FcmInterface
import com.mtjin.nomoneytrip.data.alarm.Alarm
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.reservation.Reservation
import com.mtjin.nomoneytrip.service.NotificationBody
import com.mtjin.nomoneytrip.service.NotificationBroadcastReceiver
import com.mtjin.nomoneytrip.service.NotificationData
import com.mtjin.nomoneytrip.service.ScheduledWorker
import com.mtjin.nomoneytrip.utils.*
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ReservationRepositoryImpl(
    private val database: DatabaseReference,
    private val fcmInterface: FcmInterface,
    private val context: Context
) : ReservationRepository {
    override fun insertReservation(reservation: Reservation, product: Product): Completable {
        return Completable.create { emitter ->
            database.child(RESERVATION).orderByChild(PRODUCT_ID).equalTo(product.id)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val resList = ArrayList<Reservation>()
                        for (snapshot2 in snapshot.children) {
                            snapshot2.getValue(Reservation::class.java)?.let {
                                if (it.endDateTimestamp > getTimestamp()) {
                                    resList.add(it)
                                }
                            }
                        }
                        for (res in resList) {
                            var start = reservation.startDateTimestamp
                            var end = reservation.endDateTimestamp
                            if (res.startDateTimestamp == res.endDateTimestamp || start == end) { //무박일 경우는 패스
                                continue
                            }
                            while (end > start) {
                                end -= TIMESTAMP_PER_DAY
                                if (end >= res.startDateTimestamp && end < res.endDateTimestamp) { //중복시간
                                    emitter.onError(Throwable(ERR_DUPLICATE_DATE))
                                    return
                                }
                            }
                        }
                        //예약
                        val key = database.push().key
                        reservation.id = key.toString()
                        database.child(RESERVATION).child(key.toString()).setValue(reservation)
                            .addOnSuccessListener {
                                sendNotification(reservation = reservation, product = product)
                                emitter.onComplete()
                            }.addOnFailureListener {
                                emitter.onError(it)
                            }
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    override fun sendNotification(reservation: Reservation, product: Product) {

        val title = product.title
        val message = convertTimeToFcmMessage(
            date = reservation.startDateTimestamp,
            time = product.checkIn
        )
        val startScheduledTime = reservation.startDateTimestamp
        val endScheduledTime = reservation.endDateTimestamp
        val timeDiff = endScheduledTime + TimeUnit.HOURS.toMillis(18) - System.currentTimeMillis()
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val startAlarmIntent =
            Intent(context, NotificationBroadcastReceiver::class.java).let { intent ->
                intent.putExtra(EXTRA_NOTIFICATION_TITLE, title)
                intent.putExtra(EXTRA_NOTIFICATION_MESSAGE, message)
                intent.putExtra(EXTRA_ALARM_PRODUCT_ID, product.id)
                intent.putExtra(EXTRA_ALARM_USER_ID, uuid)
                intent.putExtra(EXTRA_ALARM_TIMESTAMP, reservation.startDateTimestamp)
                intent.putExtra(EXTRA_ALARM_CASE, ALARM_START_CASE3)
                intent.putExtra(EXTRA_RESERVATION_ID, reservation.id)
                PendingIntent.getBroadcast(context, 0, intent, 0)
            }
        val dbKey = database.push().key.toString()
        database.child(ALARM).child(uuid).child(dbKey).setValue(
            Alarm(
                id = dbKey,
                productId = product.id,
                userId = uuid,
                case = ALARM_RESERVATION_COMPLETE_CASE1,
                readState = false,
                content = "$title 예약이 완료되었습니다. 이장님 수락을 기다려주세요~",
                timestamp = getTimestamp()
            )
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmMgr.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                startScheduledTime - TimeUnit.HOURS.toMillis(11),// 전날알림(13시)
                startAlarmIntent
            )
        } else {
            alarmMgr.setExact(
                AlarmManager.RTC_WAKEUP,
                startScheduledTime - TimeUnit.HOURS.toMillis(11),// 전날알림(13시)
                startAlarmIntent
            )
        }

        //종료날 리뷰요청
        val notificationData = Data.Builder()
            .putString(EXTRA_NOTIFICATION_TITLE, title)
            .putString(EXTRA_NOTIFICATION_MESSAGE, "여행은 어떠셨나요? 리뷰를 남겨주세요 :)")
            .putString(EXTRA_ALARM_PRODUCT_ID, product.id)
            .putString(EXTRA_ALARM_USER_ID, uuid)
            .putLong(EXTRA_ALARM_TIMESTAMP, reservation.endDateTimestamp)
            .putInt(EXTRA_ALARM_CASE, ALARM_REVIEW_CASE4)
            .putString(EXTRA_RESERVATION_ID, reservation.id)
            .build()
        val workRequest =
            OneTimeWorkRequestBuilder<ScheduledWorker>()
                .setInputData(notificationData)
                .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS) // 18시 알림
                .setBackoffCriteria(BackoffPolicy.LINEAR, 30000, TimeUnit.MILLISECONDS)
                .build()
        val workManager = WorkManager.getInstance(context)
        workManager.enqueue(workRequest)

        //이장님께 FCM 전송
        fcmInterface.sendNotification(
            NotificationBody(
                product.fcm,
                NotificationData(
                    title = product.title,
                    message = "사용자 예약이 있습니다 :)",
                    productId = product.id,
                    uuid = uuid,
                    alarmTimestamp = getTimestamp(),
                    alarmCase = ALARM_RESERVATION_REQUEST_CASE5,
                    isScheduled = "false",
                    reservationId = reservation.id
                )
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { Log.d(TAG, "SUCCESS") },
                onError = { Log.d(TAG, "FAIL") }
            )
    }
}