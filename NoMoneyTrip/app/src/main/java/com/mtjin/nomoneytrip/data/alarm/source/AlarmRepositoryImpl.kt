package com.mtjin.nomoneytrip.data.alarm.source

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.nomoneytrip.data.alarm.Alarm
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.reservation.Reservation
import com.mtjin.nomoneytrip.data.reservation_history.ReservationProduct
import com.mtjin.nomoneytrip.utils.*
import io.reactivex.Completable
import io.reactivex.Single

class AlarmRepositoryImpl(private val database: DatabaseReference) : AlarmRepository {
    override fun requestAlarms(): Single<List<Alarm>> {
        return Single.create<List<Alarm>> { emitter ->
            database.child(ALARM).child(uuid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val alarmList = ArrayList<Alarm>()
                        for (snapshot2 in snapshot.children) {
                            snapshot2.getValue(Alarm::class.java)?.let {
                                if (alarmList.isNotEmpty()) {
                                    alarmList.add(alarmList.size - 1, it)
                                } else {
                                    alarmList.add(it)
                                }
                            }
                        }
                        alarmList.sortByDescending { it.timestamp }
                        emitter.onSuccess(alarmList)
                    }

                })
        }
    }

    override fun updateAlarmReadState(alarm: Alarm): Completable {
        return Completable.create { emitter ->
            val stateMap = HashMap<String, Any>()
            stateMap[READ_STATE] = true
            database.child(ALARM).child(uuid).child(alarm.id).updateChildren(stateMap)
                .addOnSuccessListener {
                    emitter.onComplete()
                }.addOnFailureListener {
                    emitter.onError(it)
                }

        }
    }

    override fun requestReservation(alarm: Alarm): Single<ReservationProduct> {
        return Single.create<ReservationProduct> { emitter ->
            database.child(PRODUCT).child(alarm.productId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.getValue(Product::class.java)?.let { product ->
                            database.child(RESERVATION).orderByChild(USER_ID).equalTo(uuid)
                                .addValueEventListener(object : ValueEventListener {
                                    override fun onCancelled(error: DatabaseError) {
                                        emitter.onError(error.toException())
                                    }

                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        for (reserveSnapShot: DataSnapshot in snapshot.children) {
                                            reserveSnapShot.getValue(Reservation::class.java)?.let {

                                                if (product.id == it.productId) {
                                                    emitter.onSuccess(
                                                        ReservationProduct(
                                                            reservation = it,
                                                            product = product
                                                        )
                                                    )
                                                    return
                                                }
                                            }
                                        }
                                        emitter.onError(Throwable("requestReservations 에러"))
                                    }
                                })
                        }
                    }
                })
        }
    }

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