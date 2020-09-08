package com.mtjin.nomoneytrip.data.reservation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Reservation(
    var id: String = "",
    var userId: String = "",
    var option: String = "",
    var productId: String = "",
    var num: String = "",
    var state: Boolean = true, //예약취소시 false
    var startDateTimestamp: Long = 0,
    var endDateTimestamp: Long = 0
) : Parcelable