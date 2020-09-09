package com.mtjin.nomoneytrip.data.reservation_phase_first.source

import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.reservation.Reservation
import io.reactivex.Flowable

interface ReservationPhaseFirstRepository {
    fun requestReservations(product: Product): Flowable<List<Reservation>>
}