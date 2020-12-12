package com.mtjin.nomoneytrip.service.work_manager

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
import com.mtjin.nomoneytrip.data.master_login.MasterUser
import com.mtjin.nomoneytrip.data.reservation.Reservation
import com.mtjin.nomoneytrip.utils.*

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
                            Log.d(
                                TAG,
                                "ScheduledWorker Firebase DB error -> " + error.toException()
                            )
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
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
                                                timestamp = timestamp,
                                                reservationId = reservationId
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
            } else if (case == ALARM_RESERVATION_REQUEST_CASE5) {
                Firebase.database.reference.child(MASTER_USER).orderByChild(PRODUCT_ID).equalTo(
                    productId
                ).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        Log.d(TAG, error.toException().toString())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (snapshot2 in snapshot.children) {
                            snapshot2.getValue(MasterUser::class.java)?.let { masterUser ->
                                Firebase.database.reference.child(ALARM).child(masterUser.id)
                                    .child(dbKey)
                                    .setValue(
                                        Alarm(
                                            id = dbKey,
                                            productId = productId,
                                            userId = masterUser.id,
                                            case = case,
                                            readState = false,
                                            content = message,
                                            timestamp = timestamp,
                                            reservationId = reservationId
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
            } else { //이장님 수락,거절, 예약완료
//                Firebase.database.reference.child(ALARM).child(userId).child(dbKey).setValue(
//                    Alarm(
//                        id = dbKey,
//                        productId = productId,
//                        userId = userId,
//                        case = case,
//                        readState = false,
//                        content = message,
//                        timestamp = timestamp
//                    )
//                )
                NotificationUtil(applicationContext).showNotification(
                    title,
                    message
                )
            }
        }
        return Result.success()

    }
}