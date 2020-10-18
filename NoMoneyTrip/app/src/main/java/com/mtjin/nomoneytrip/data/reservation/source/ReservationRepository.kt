package com.mtjin.nomoneytrip.data.reservation.source

import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.reservation.Reservation
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.ResponseBody

interface ReservationRepository {
    fun insertReservation(reservation: Reservation, product: Product): Single<Reservation>
    fun deleteReservation(reservation: Reservation): Completable
    fun sendNotification(reservation: Reservation, product: Product): Single<ResponseBody>
}