package com.mtjin.nomoneytrip.data.reservation_history.source

import com.mtjin.nomoneytrip.data.reservation.Reservation
import com.mtjin.nomoneytrip.data.reservation_history.ReservationHistory
import io.reactivex.Completable
import io.reactivex.Flowable

interface ReservationHistoryRepository {
    fun requestReservations(): Flowable<List<ReservationHistory>>
    fun deleteReservation(reservation: Reservation): Completable
}