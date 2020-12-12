package com.mtjin.nomoneytrip.service

import com.mtjin.nomoneytrip.service.notification.NotificationData

data class NotificationBody(val to: String, val data: NotificationData)