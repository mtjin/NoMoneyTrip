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
    val reviewed: Boolean = false, // 고객 리뷰 남김 유무
    var startDateTimestamp: Long = 0,
    var endDateTimestamp: Long = 0,
    var state: Int = 0 // 0:기본값 대기상태(미정), 1: 이장 예약거절, 2:이장 예약수락, 3: 사용자예약취소
) : Parcelable