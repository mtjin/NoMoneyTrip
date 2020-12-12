package com.mtjin.nomoneytrip.service.notification

data class NotificationData(
    val title: String,
    val message: String,
    val productId: String,
    val uuid: String,
    val alarmTimestamp: Long,
    val alarmCase: Int,
    var isScheduled: String = "false",
    var reservationId: String = ""
)