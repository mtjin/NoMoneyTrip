package com.mtjin.nomoneytrip.data.local_page


import com.google.gson.annotations.SerializedName

data class Items(
    @SerializedName("item")
    val item: List<Item>
)