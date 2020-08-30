package com.mtjin.nomoneytrip.data.local_page


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("body")
    val body: Body,
    @SerializedName("header")
    val header: Header
)