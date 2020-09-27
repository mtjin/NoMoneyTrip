package com.mtjin.nomoneytrip.data.master_write

data class MasterLetter(
    var id: String = "",
    var userId: String = "",
    var productId: String = "",
    var title: String = "",
    var content: String = "",
    var timestamp: Long = 0
)