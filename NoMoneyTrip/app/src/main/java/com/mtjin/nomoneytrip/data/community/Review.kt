package com.mtjin.nomoneytrip.data.community

data class Review(
    var id: String = "",
    var userId: String = "",
    var productId: String = "",
    var reservationId: String = "",
    var timestamp: Long = 0,
    var image: String = "",
    var content: String = "",
    var recommend: Long = 0,
    var city: String = ""
)