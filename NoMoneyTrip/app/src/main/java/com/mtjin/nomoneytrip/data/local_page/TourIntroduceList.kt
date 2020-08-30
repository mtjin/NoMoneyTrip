package com.mtjin.nomoneytrip.data.local_page


import com.google.gson.annotations.SerializedName

data class TourIntroduceList(
    @SerializedName("item")
    val tourIntroduce: List<TourIntroduce>
)