package com.mtjin.nomoneytrip.data.local_page


import com.google.gson.annotations.SerializedName

data class TourIntroduceResponse(
    @SerializedName("body")
    val tourIntroduceBody: TourIntroduceBody,
    @SerializedName("header")
    val tourIntroduceHeader: TourIntroduceHeader
)