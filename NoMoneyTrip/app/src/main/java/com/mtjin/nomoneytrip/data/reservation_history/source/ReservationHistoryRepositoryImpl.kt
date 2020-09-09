package com.mtjin.nomoneytrip.data.reservation_history.source

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.reservation.Reservation
import com.mtjin.nomoneytrip.data.reservation_history.ReservationProduct
import com.mtjin.nomoneytrip.utils.PRODUCT
import com.mtjin.nomoneytrip.utils.RESERVATION
import com.mtjin.nomoneytrip.utils.USER_ID
import com.mtjin.nomoneytrip.utils.uuid
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable

class ReservationHistoryRepositoryImpl(private val database: DatabaseReference) :
    ReservationHistoryRepository {
    private val productList = ArrayList<Product>()

    override fun requestReservations(): Flowable<List<ReservationProduct>> {
        return Flowable.create<List<ReservationProduct>>(
            { emitter ->
                database.child(PRODUCT).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        productList.clear()
                        for (snapshot2: DataSnapshot in snapshot.children) {
                            snapshot2.getValue(Product::class.java)?.let {
                                productList.add(it)
                            }
                        }
                        database.child(RESERVATION).orderByChild(USER_ID).equalTo(uuid)
                            .addValueEventListener(object : ValueEventListener {
                                override fun onCancelled(error: DatabaseError) {
                                    emitter.onError(error.toException())
                                }

                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val list = ArrayList<ReservationProduct>()
                                    for (reserveSnapShot: DataSnapshot in snapshot.children) {
                                        reserveSnapShot.getValue(Reservation::class.java)?.let {
                                            for (product in productList) {
                                                if (product.id == it.productId) {
                                                    list.add(
                                                        ReservationProduct(
                                                            reservation = it,
                                                            product = product
                                                        )
                                                    )
                                                    break
                                                }
                                            }
                                        }
                                    }
                                    emitter.onNext(list)
                                }

                            })
                    }

                })

            }, BackpressureStrategy.BUFFER
        )
    }

    override fun deleteReservation(reservation: Reservation): Completable {
        return Completable.create { emitter ->
            database.child(RESERVATION).child(reservation.id).removeValue().addOnSuccessListener {
                emitter.onComplete()
            }.addOnFailureListener {
                emitter.onError(it)
            }
        }
    }
}