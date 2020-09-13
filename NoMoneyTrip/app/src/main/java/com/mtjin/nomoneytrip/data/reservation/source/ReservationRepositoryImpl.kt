package com.mtjin.nomoneytrip.data.reservation.source

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.reservation.Reservation
import com.mtjin.nomoneytrip.service.NotificationBroadcastReceiver
import com.mtjin.nomoneytrip.utils.*
import io.reactivex.Completable
import java.util.concurrent.TimeUnit

class ReservationRepositoryImpl(
    private val database: DatabaseReference,
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
                                Log.d("AAAABBB","CCCCCC")
                                emitter.onComplete()
                            }.addOnFailureListener {
                                emitter.onError(it)
                            }
                    }
                })
        }
    }

    override fun sendNotification(reservation: Reservation, product: Product) {
        val title = product.title
        val message = convertTimeToFcmMessage(
            date = reservation.startDateTimestamp,
            time = product.checkIn
        )
        val scheduledTime = reservation.startDateTimestamp
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent =
            Intent(context, NotificationBroadcastReceiver::class.java).let { intent ->
                intent.putExtra(EXTRA_NOTIFICATION_TITLE, title)
                intent.putExtra(EXTRA_NOTIFICATION_MESSAGE, message)
                PendingIntent.getBroadcast(context, 0, intent, 0)
            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmMgr.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                scheduledTime - TimeUnit.HOURS.toMillis(24),// 전날알림(12시)
                alarmIntent
            )
        } else {
            alarmMgr.setExact(
                AlarmManager.RTC_WAKEUP,
                scheduledTime - TimeUnit.HOURS.toMillis(24),// 전날알림(12시)
                alarmIntent
            )
        }
    }
}