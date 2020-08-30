package com.mtjin.nomoneytrip.data.local_page


import com.google.gson.annotations.SerializedName

data class TourIntroduceResponseBody(
    @SerializedName("response")
    val tourIntroduceResponse: TourIntroduceResponse
)