package com.mtjin.nomoneytrip.fcm

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mtjin.nomoneytrip.utils.EXTRA_NOTIFICATION_MESSAGE
import com.mtjin.nomoneytrip.utils.EXTRA_NOTIFICATION_TITLE
import com.mtjin.nomoneytrip.utils.NotificationUtil

class ScheduledWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {

        // Get Notification Data
        val title = inputData.getString(EXTRA_NOTIFICATION_TITLE).toString()
        val message = inputData.getString(EXTRA_NOTIFICATION_MESSAGE).toString()
        // FCM 전송
        NotificationUtil(applicationContext).showNotification(
            title,
            message
        )

        return Result.success()

    }
}