package com.mtjin.nomoneytrip.service

data class NotificationData(
    val title: String,
    val body: String,
    var isScheduled: String = "false",
    var scheduledTime: String = ""
)