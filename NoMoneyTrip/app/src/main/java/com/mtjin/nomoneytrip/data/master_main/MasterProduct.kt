package com.mtjin.nomoneytrip.data.master_main

import android.os.Parcelable
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.data.reservation.Reservation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MasterProduct(
    var reservation: Reservation = Reservation(),
    var user: User = User()
) : Parcelable