package com.mtjin.nomoneytrip.data.tour_detail


import com.google.gson.annotations.SerializedName

data class Body(
    @SerializedName("items")
    val items: Items,
    @SerializedName("numOfRows")
    val numOfRows: String,
    @SerializedName("pageNo")
    val pageNo: String,
    @SerializedName("totalCount")
    val totalCount: String
)