package com.mtjin.nomoneytrip.data.reservation.source

import com.mtjin.nomoneytrip.data.reservation.Reservation
import io.reactivex.Completable

interface ReservationRepository {
    fun requestReservation(reservation: Reservation): Completable
}