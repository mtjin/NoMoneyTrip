package com.mtjin.nomoneytrip.data.local_page


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TourIntroduce(
    @SerializedName("addr1")
    val addr1: String,
    @SerializedName("addr2")
    val addr2: String,
    @SerializedName("areacode")
    val areacode: Int,
    @SerializedName("cat1")
    val cat1: String,
    @SerializedName("cat2")
    val cat2: String,
    @SerializedName("cat3")
    val cat3: String,
    @SerializedName("contentid")
    val contentid: Int,
    @SerializedName("contenttypeid")
    val contenttypeid: Int,
    @SerializedName("createdtime")
    val createdtime: Long,
    @SerializedName("firstimage")
    val firstimage: String,
    @SerializedName("firstimage2")
    val firstimage2: String,
    @SerializedName("mapx")
    val mapx: Double,
    @SerializedName("mapy")
    val mapy: Double,
    @SerializedName("mlevel")
    val mlevel: Int,
    @SerializedName("modifiedtime")
    val modifiedtime: Long,
    @SerializedName("readcount")
    val readcount: Int,
    @SerializedName("sigungucode")
    val sigungucode: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("zipcode")
    val zipcode: String
) : Parcelable