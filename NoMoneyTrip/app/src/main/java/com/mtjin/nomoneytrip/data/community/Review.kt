package com.mtjin.nomoneytrip.data.community

data class Review(
    var id: String = "",
    var userId: String = "",
    var timestamp: Long = 0,
    var image: String = "",
    var title: String = "",
    var content: String = "",
    var recommend: Long = 0,
    var local: String = ""
)