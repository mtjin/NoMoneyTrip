package com.mtjin.nomoneytrip.data.login

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: String = "",
    var name: String = "",
    var fcm: String = "",
    var email: String = "",
    var pw: String = "",
    var image: String = "",
    var tel: String = ""
) : Parcelable