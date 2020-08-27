package com.mtjin.nomoneytrip.fcm

data class NotificationData(
    val title: String,
    val body: String,
    var isScheduled: String = "false",
    var scheduledTime: String = ""
)