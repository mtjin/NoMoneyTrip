package com.mtjin.nomoneytrip.data.reservation

data class Reservation(
    var id: String = "",
    var productId: String = "",
    var startDateTimestamp: String = "",
    var endDateTimestamp: String = "",
    var time: String = ""
)