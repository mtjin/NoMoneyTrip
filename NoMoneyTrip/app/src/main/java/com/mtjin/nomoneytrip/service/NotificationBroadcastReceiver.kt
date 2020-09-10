package com.mtjin.nomoneytrip.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.BackoffPolicy
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.mtjin.nomoneytrip.utils.EXTRA_NOTIFICATION_MESSAGE
import com.mtjin.nomoneytrip.utils.EXTRA_NOTIFICATION_TITLE
import java.util.concurrent.TimeUnit

class NotificationBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        intent?.let {
            val title = it.getStringExtra(EXTRA_NOTIFICATION_TITLE).toString()
            val message = it.getStringExtra(EXTRA_NOTIFICATION_MESSAGE).toString()
            // Create Notification Data
            val notificationData = Data.Builder()
                .putString(EXTRA_NOTIFICATION_TITLE, title)
                .putString(EXTRA_NOTIFICATION_MESSAGE, message)
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
