package com.mtjin.nomoneytrip.data.alarm

data class Alarm(
    var id: String = "",
    var productId: String = "",
    var userId: String = "",
    var case: Int = 0,
    var readState: Boolean = false,
    var content: String = "",
    var timestamp: Long = 0,
    var reservationId: String = ""
)