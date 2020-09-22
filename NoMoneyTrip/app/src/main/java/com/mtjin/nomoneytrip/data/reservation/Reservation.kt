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
    var state: Boolean = true, // 고객 예약취소여부
    val reviewed: Boolean = false, // 고객 리뷰 남김 유무
    var startDateTimestamp: Long = 0,
    var endDateTimestamp: Long = 0,
    var masterState: Int = 0 // 이장님 0:기본(미정), 1: 예약거절, 2:예약수락 구분
) : Parcelable