package com.mtjin.nomoneytrip.data.login

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: String = "",
    var name: String = "",
    var fcm: String = "",
    var email: String = "",
    var pw: String = "",
    var image: String = ""
) : Parcelable