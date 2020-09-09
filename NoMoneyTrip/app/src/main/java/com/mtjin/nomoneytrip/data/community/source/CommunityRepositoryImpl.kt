package com.mtjin.nomoneytrip.data.community.source

import com.mtjin.nomoneytrip.data.reservation.Reservation
import io.reactivex.Single

class CommunityRepositoryImpl : CommunityRepository {
    override fun requestMyReservations(): Single<List<Reservation>> {
        TODO("Not yet implemented")
    }
}