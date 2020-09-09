package com.mtjin.nomoneytrip.data.reservation_phase_first.source

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.reservation.Reservation
import com.mtjin.nomoneytrip.utils.PRODUCT_ID
import com.mtjin.nomoneytrip.utils.RESERVATION
import com.mtjin.nomoneytrip.utils.getTimestamp
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

class ReservationPhaseFirstRepositoryImpl(private val database: DatabaseReference) :
    ReservationPhaseFirstRepository {
    override fun requestReservations(product: Product): Flowable<List<Reservation>> {
        return Flowable.create<List<Reservation>>({ emitter ->
            database.child(RESERVATION).orderByChild(PRODUCT_ID).equalTo(product.id)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val list = ArrayList<Reservation>()
                        for (snapshot2 in snapshot.children) {
                            snapshot2.getValue(Reservation::class.java)?.let {
                                if (it.endDateTimestamp > getTimestamp()) {
                                    list.add(it)
                                }

                            }
                        }
                        emitter.onNext(list)
                    }

                })

        }, BackpressureStrategy.BUFFER)
    }
}