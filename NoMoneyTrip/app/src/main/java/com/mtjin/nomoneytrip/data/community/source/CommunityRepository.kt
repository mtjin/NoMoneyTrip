package com.mtjin.nomoneytrip.data.community.source

import com.mtjin.nomoneytrip.data.reservation.Reservation
import io.reactivex.Single

interface CommunityRepository {
    fun requestMyReservations(): Single<List<Reservation>>
}