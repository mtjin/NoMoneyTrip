package com.mtjin.nomoneytrip.data.local_page


import com.google.gson.annotations.SerializedName

data class TourIntroduceBody(
    @SerializedName("items")
    val tourIntroduceList: TourIntroduceList,
    @SerializedName("numOfRows")
    val numOfRows: Int,
    @SerializedName("pageNo")
    val pageNo: Int,
    @SerializedName("totalCount")
    val totalCount: Int
)