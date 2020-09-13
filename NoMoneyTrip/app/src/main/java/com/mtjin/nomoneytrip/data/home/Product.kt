package com.mtjin.nomoneytrip.data.home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    var id: String = "",
    var imageList: List<String> = ArrayList(),
    var hashTagList: List<String> = ArrayList(),
    var optionList: List<String> = ArrayList(),
    var favoriteList: ArrayList<String> = ArrayList(),
    var title: String = "",
    var content: String = "",
    var city: String = "",
    var address: String = "",
    var homePage: String = "",
    var xPos: String = "",
    var yPos: String = "",
    var checkIn: String = "",
    var checkOut: String = "",
    var phone: String = "",
    var time: String = "",
    var ratingList: List<Float> = ArrayList(),
    var internet: Boolean = false,
    var parking: Boolean = false,
    var market: Boolean = false,
    var animal: Boolean = false

) : Parcelable