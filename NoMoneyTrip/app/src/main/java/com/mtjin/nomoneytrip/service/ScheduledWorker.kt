package com.mtjin.nomoneytrip.service

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mtjin.nomoneytrip.data.alarm.Alarm
import com.mtjin.nomoneytrip.utils.*

class ScheduledWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        // Get Notification Data
        val title = inputData.getString(EXTRA_NOTIFICATION_TITLE).toString()
        val message = inputData.getString(EXTRA_NOTIFICATION_MESSAGE).toString()
        val productId = inputData.getString(EXTRA_ALARM_PRODUCT_ID).toString()
        val userId = inputData.getString(EXTRA_ALARM_USER_ID).toString()
        val timestamp = inputData.getLong(EXTRA_ALARM_TIMESTAMP, 0)
        val case = inputData.getInt(EXTRA_ALARM_CASE, 0)
        val dbKey = Firebase.database.reference.push().key.toString()
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
        // FCM 전송
        NotificationUtil(applicationContext).showNotification(
            title,
            message
        )

        return Result.success()

    }
}