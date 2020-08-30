package com.mtjin.nomoneytrip.data.local_page


import com.google.gson.annotations.SerializedName

data class TourIntroduceHeader(
    @SerializedName("resultCode")
    val resultCode: String,
    @SerializedName("resultMsg")
    val resultMsg: String
)