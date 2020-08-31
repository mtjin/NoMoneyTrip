package com.mtjin.nomoneytrip.data.home

data class Product(
    var id: String = "",
    var image: String = "",
    var content: String = "",
    var city: String = "",
    var local: String = "",
    var hashTagList: List<String> = ArrayList()
)