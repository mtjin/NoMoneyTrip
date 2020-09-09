package com.mtjin.nomoneytrip.data.community.source

import com.google.firebase.database.DatabaseReference
import com.mtjin.nomoneytrip.data.reservation.Reservation
import io.reactivex.Single

class CommunityRepositoryImpl(private val database: DatabaseReference) : CommunityRepository {
    override fun requestMyReservations(): Single<List<Reservation>> {
        return Single.create<List<Reservation>> { emitter ->

        }
    }
}