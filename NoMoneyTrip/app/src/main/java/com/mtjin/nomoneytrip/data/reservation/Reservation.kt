package com.mtjin.nomoneytrip.data.reservation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Reservation(
    var id: Long = 0,
    var userId: String = "",
    var productId: String = "",
    var num: String = "",
    var startDateTimestamp: Long = 0,
    var endDateTimestamp: Long = 0
) : Parcelable