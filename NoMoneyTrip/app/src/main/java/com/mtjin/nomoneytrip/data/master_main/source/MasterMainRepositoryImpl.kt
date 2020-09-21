package com.mtjin.nomoneytrip.data.master_main.source

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.data.master_main.MasterProduct
import com.mtjin.nomoneytrip.data.reservation.Reservation
import com.mtjin.nomoneytrip.utils.PRODUCT_ID
import com.mtjin.nomoneytrip.utils.RESERVATION
import com.mtjin.nomoneytrip.utils.USER
import com.mtjin.nomoneytrip.utils.masterProductId
import io.reactivex.Single

class MasterMainRepositoryImpl(private val database: DatabaseReference) : MasterMainRepository {
    override fun requestNewMasterProducts(): Single<List<MasterProduct>> {
        return Single.create { emitter ->
            database.child(RESERVATION).orderByChild(PRODUCT_ID).equalTo(masterProductId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val reservationList = ArrayList<Reservation>()
                        for (snapshot2 in snapshot.children) {
                            snapshot2.getValue(Reservation::class.java)?.let {
                                if (it.state && !it.isAccepted) {
                                    reservationList.add(it)
                                }
                            }
                        }
                        database.child(USER)
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onCancelled(error: DatabaseError) {
                                    emitter.onError(error.toException())
                                }

                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val userList = ArrayList<User>()
                                    for (snapshot2 in snapshot.children) {
                                        snapshot2.getValue(User::class.java)?.let {
                                            userList.add(it)
                                        }
                                    }
                                    val masterProductList = ArrayList<MasterProduct>()
                                    for (reservation in reservationList) {
                                        for (user in userList) {
                                            if (user.id == reservation.userId) {
                                                masterProductList.add(
                                                    MasterProduct(
                                                        reservation = reservation,
                                                        user = user
                                                    )
                                                )
                                            }
                                        }
                                    }
                                    emitter.onSuccess(masterProductList)
                                }

                            })
                    }

                })
        }
    }

    override fun requestAcceptedMasterProducts(): Single<List<MasterProduct>> {
        return Single.create { emitter ->
            database.child(RESERVATION).orderByChild(PRODUCT_ID).equalTo(masterProductId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val reservationList = ArrayList<Reservation>()
                        for (snapshot2 in snapshot.children) {
                            snapshot2.getValue(Reservation::class.java)?.let {
                                if (it.state && it.isAccepted) {
                                    reservationList.add(it)
                                }
                            }
                        }
                        database.child(USER)
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onCancelled(error: DatabaseError) {
                                    emitter.onError(error.toException())
                                }

                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val userList = ArrayList<User>()
                                    for (snapshot2 in snapshot.children) {
                                        snapshot2.getValue(User::class.java)?.let {
                                            userList.add(it)
                                        }
                                    }
                                    val masterProductList = ArrayList<MasterProduct>()
                                    for (reservation in reservationList) {
                                        for (user in userList) {
                                            if (user.id == reservation.userId) {
                                                masterProductList.add(
                                                    MasterProduct(
                                                        reservation = reservation,
                                                        user = user
                                                    )
                                                )
                                            }
                                        }
                                    }
                                    emitter.onSuccess(masterProductList)
                                }

                            })
                    }

                })
        }
    }
}