package com.mtjin.nomoneytrip.data.home

data class Product(
    var id: String = "",
    var image: List<String> = ArrayList(),
    var content: String = "",
    var city: String = "",
    var local: String = "",
    var hashTagList: List<String> = ArrayList()
)