package com.mtjin.nomoneytrip.service

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mtjin.nomoneytrip.data.alarm.Alarm
import com.mtjin.nomoneytrip.data.reservation.Reservation
import com.mtjin.nomoneytrip.utils.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ScheduledWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        // 알람 On/Off
        val prefManager = PreferenceManager(applicationContext)
        if (prefManager.alarmSetting) {
            // Get Notification Data
            val title = inputData.getString(EXTRA_NOTIFICATION_TITLE).toString()
            val message = inputData.getString(EXTRA_NOTIFICATION_MESSAGE).toString()
            val productId = inputData.getString(EXTRA_ALARM_PRODUCT_ID).toString()
            val userId = inputData.getString(EXTRA_ALARM_USER_ID).toString()
            val timestamp = inputData.getLong(EXTRA_ALARM_TIMESTAMP, 0)
            val case = inputData.getInt(EXTRA_ALARM_CASE, 0)
            val reservationId = inputData.getString(EXTRA_RESERVATION_ID).toString()
            val dbKey = Firebase.database.reference.push().key.toString()
            if (case == ALARM_START_CASE3 || case == ALARM_REVIEW_CASE4) { // (시작전날 및 종료날리뷰 알림의 경우) 해당예약시간까지 이장님이 수락한 경우 FCM 전송
                Firebase.database.reference.child(RESERVATION).child(reservationId)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            Log.d(TAG, "ScheduledWorker Firebase DB error -> " + error.toException())
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            val s = (snapshot.getValue(Reservation::class.java))
                            snapshot.getValue(Reservation::class.java)?.let { reservation ->
                                if (reservation.state == 2 || reservation.state == 0) {
                                    Firebase.database.reference.child(ALARM).child(userId)
                                        .child(dbKey)
                                        .setValue(
                                            Alarm(
                                                id = dbKey,
                                                productId = productId,
                                                userId = userId,
                                                case = case,
                                                readState = false,
                                                content = message,
                                                timestamp = timestamp
                                            )
                                        )
                                    NotificationUtil(applicationContext).showNotification(
                                        title,
                                        message
                                    )
                                }
                            }
                        }
                    })
            } else {
                Firebase.database.reference.child(ALARM).child(userId).child(dbKey).setValue(
                    Alarm(
                        id = dbKey,
                        productId = productId,
                        userId = userId,
                        case = case,
                        readState = false,
                        content = message,
                        timestamp = timestamp
                    )
                )
                NotificationUtil(applicationContext).showNotification(
                    title,
                    message
                )
            }
        }
        return Result.success()

    }
}