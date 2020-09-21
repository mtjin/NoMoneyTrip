package com.mtjin.nomoneytrip.service

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
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mtjin.nomoneytrip.utils.*
import java.util.concurrent.TimeUnit


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val to = remoteMessage.to.toString()
        Log.d("AAAAA", remoteMessage.toString())
        if (remoteMessage.data["isScheduled"] == "false") { // 즉시 전송
            sendNotification(remoteMessage)
        } else { // 예약전송

        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val notificationData = Data.Builder()
            .putString(EXTRA_NOTIFICATION_TITLE, remoteMessage.data["title"].toString())
            .putString(EXTRA_NOTIFICATION_MESSAGE, remoteMessage.data["message"].toString())
            .putString(EXTRA_ALARM_PRODUCT_ID, remoteMessage.data["productId"].toString())
            .putString(EXTRA_ALARM_USER_ID, remoteMessage.data["uuid"].toString())
            .putLong(EXTRA_ALARM_TIMESTAMP, remoteMessage.data["alarmTimestamp"]?.toLong()!!)
            .putInt(EXTRA_ALARM_CASE, remoteMessage.data["alarmCase"]!!.toInt())
            .putString(EXTRA_RESERVATION_ID, remoteMessage.data["reservationId"].toString())
            .build()
        val workRequest =
            OneTimeWorkRequestBuilder<ScheduledWorker>()
                .setInputData(notificationData)
                .setBackoffCriteria(BackoffPolicy.LINEAR, 30000, TimeUnit.MILLISECONDS)
                .build()
        val workManager = WorkManager.getInstance(applicationContext)
        workManager.enqueue(workRequest)
    }

    private fun scheduleAlarm(
        scheduledTime: String,
        title: String,
        message: String
    ) {

        val alarmMgr = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent =
            Intent(applicationContext, NotificationBroadcastReceiver::class.java).let { intent ->
                intent.putExtra(EXTRA_NOTIFICATION_TITLE, title)
                intent.putExtra(EXTRA_NOTIFICATION_MESSAGE, message)
                PendingIntent.getBroadcast(applicationContext, 0, intent, 0)
            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmMgr.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                scheduledTime.toLong() - TimeUnit.HOURS.toMillis(15),// 15분전알림
                alarmIntent
            )
        } else {
            alarmMgr.setExact(
                AlarmManager.RTC_WAKEUP,
                scheduledTime.toLong() - TimeUnit.HOURS.toMillis(15),// 15분전알림
                alarmIntent
            )
        }
    }

    override fun onNewToken(token: String) {
        Log.d("FCM new token -> ", token)
    }
}