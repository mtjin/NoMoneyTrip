package com.mtjin.nomoneytrip.data.alarm.source

import com.mtjin.nomoneytrip.data.alarm.Alarm
import com.mtjin.nomoneytrip.data.reservation.Reservation
import com.mtjin.nomoneytrip.data.reservation_history.ReservationProduct
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface AlarmRepository {
    fun requestAlarms(): Single<List<Alarm>>
    fun updateAlarmReadState(alarm : Alarm): Completable
    fun requestReservation(alarm: Alarm): Single<ReservationProduct>
    fun requestMyReservations(): Single<List<ReservationProduct>>
}