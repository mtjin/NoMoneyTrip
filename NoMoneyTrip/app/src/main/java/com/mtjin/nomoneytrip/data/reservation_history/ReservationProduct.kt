package com.mtjin.nomoneytrip.data.reservation_history

import android.os.Parcelable
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.reservation.Reservation
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ReservationProduct(
    var id: String = "",
    var reservation: Reservation = Reservation(),
    var product: Product = Product()
) : Parcelable