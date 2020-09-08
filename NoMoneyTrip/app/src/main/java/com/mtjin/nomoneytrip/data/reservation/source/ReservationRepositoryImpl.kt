package com.mtjin.nomoneytrip.data.reservation.source

import com.google.firebase.database.DatabaseReference
import com.mtjin.nomoneytrip.data.reservation.Reservation
import com.mtjin.nomoneytrip.utils.RESERVATION
import io.reactivex.Completable

class ReservationRepositoryImpl(private val database: DatabaseReference) : ReservationRepository {
    override fun requestReservation(reservation: Reservation): Completable {
        return Completable.create { emitter ->
            val key = database.push().key
            reservation.id = key.toString()
            database.child(RESERVATION).child(key.toString()).setValue(reservation)
                .addOnSuccessListener {
                    emitter.onComplete()
                }.addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }
}