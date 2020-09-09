package com.mtjin.nomoneytrip.data.reservation.source

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.reservation.Reservation
import com.mtjin.nomoneytrip.utils.*
import io.reactivex.Completable

class ReservationRepositoryImpl(private val database: DatabaseReference) : ReservationRepository {
    override fun insertReservation(reservation: Reservation, product: Product): Completable {
        return Completable.create { emitter ->
            database.child(RESERVATION).orderByChild(PRODUCT_ID).equalTo(product.id)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val resList = ArrayList<Reservation>()
                        for (snapshot2 in snapshot.children) {
                            snapshot2.getValue(Reservation::class.java)?.let {
                                if (it.endDateTimestamp > getTimestamp()) {
                                    resList.add(it)
                                }
                            }
                        }
                        for (res in resList) {
                            var start = reservation.startDateTimestamp
                            var end = reservation.endDateTimestamp
                            if (res.startDateTimestamp == res.endDateTimestamp || start == end) { //무박일 경우는 패스
                                continue
                            }
                            while (end > start) {
                                end -= TIMESTAMP_PER_DAY
                                if (end >= res.startDateTimestamp && end < res.endDateTimestamp) { //중복시간
                                    emitter.onError(Throwable(ERR_DUPLICATE_DATE))
                                    return
                                }
                            }
                        }
                        //예약
                        val key = database.push().key
                        reservation.id = key.toString()
                        database.child(RESERVATION).child(key.toString()).setValue(reservation)
                            .addOnSuccessListener {
                                emitter.onComplete()
                            }.addOnFailureListener {
                                emitter.onError(it)
                            }
                    }
                })
        }
    }
}