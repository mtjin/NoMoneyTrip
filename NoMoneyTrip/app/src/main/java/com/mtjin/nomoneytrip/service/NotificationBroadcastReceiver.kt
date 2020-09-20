package com.mtjin.nomoneytrip.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.BackoffPolicy
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.mtjin.nomoneytrip.utils.*
import java.util.concurrent.TimeUnit

class NotificationBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        intent?.let {
            val title = it.getStringExtra(EXTRA_NOTIFICATION_TITLE).toString()
            val message = it.getStringExtra(EXTRA_NOTIFICATION_MESSAGE).toString()
            val productId = intent.getStringExtra(EXTRA_ALARM_PRODUCT_ID).toString()
            val userId = intent.getStringExtra(EXTRA_ALARM_USER_ID)
            val timestamp = intent.getLongExtra(EXTRA_ALARM_TIMESTAMP, 0)
            val case = intent.getIntExtra(EXTRA_ALARM_CASE, 0)
            // Create Notification Data
            val notificationData = Data.Builder()
                .putString(EXTRA_NOTIFICATION_TITLE, title)
                .putString(EXTRA_NOTIFICATION_MESSAGE, message)
                .putString(EXTRA_ALARM_PRODUCT_ID, productId)
                .putString(EXTRA_ALARM_USER_ID, userId)
                .putLong(EXTRA_ALARM_TIMESTAMP, timestamp)
                .putInt(EXTRA_ALARM_CASE, case)
                .build()

            val workRequest =
                OneTimeWorkRequestBuilder<ScheduledWorker>()
                    .setInputData(notificationData)
                    .setBackoffCriteria(BackoffPolicy.LINEAR, 30000, TimeUnit.MILLISECONDS)
                    .build()

            val workManager = WorkManager.getInstance(context)
            workManager.enqueue(workRequest)
        }
    }
}
