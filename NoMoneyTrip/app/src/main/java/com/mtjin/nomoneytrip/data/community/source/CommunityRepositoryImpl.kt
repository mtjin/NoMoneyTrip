package com.mtjin.nomoneytrip.data.community.source

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
import io.reactivex.Single

class CommunityRepositoryImpl(private val database: DatabaseReference) : CommunityRepository {
    override fun requestMyReservations(): Single<List<ReservationProduct>> {
        return Single.create<List<ReservationProduct>> { emitter ->
            val productList = ArrayList<Product>()
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
                                emitter.onSuccess(list)
                            }
                        })
                }
            })
        }
    }
}