package com.mtjin.nomoneytrip.data.reservation_history.source

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.nomoneytrip.api.FcmInterface
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.reservation.Reservation
import com.mtjin.nomoneytrip.data.reservation_history.ReservationProduct
import com.mtjin.nomoneytrip.service.NotificationBody
import com.mtjin.nomoneytrip.service.notification.NotificationData
import com.mtjin.nomoneytrip.utils.*
import com.mtjin.nomoneytrip.utils.extensions.convertTimeToUserDenyFcmMessage
import com.mtjin.nomoneytrip.utils.extensions.getTimestamp
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ReservationHistoryRepositoryImpl(
    private val database: DatabaseReference,
    private val fcmInterface: FcmInterface
) :
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
                                    list.sortByDescending { it.reservation.startDateTimestamp }
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

    override fun updateReservationCancel(reservationProduct: ReservationProduct): Completable {
        return Completable.create { emitter ->
            val map = HashMap<String, Any>()
            map[STATE] = RESERVATION_USER_DENY_CODE
            database.child(RESERVATION).child(reservationProduct.reservation.id).updateChildren(map)
                .addOnSuccessListener {
                    sendFCM(reservationProduct = reservationProduct)
                    emitter.onComplete()
                }.addOnFailureListener {
                    emitter.onError(it)
                }

        }
    }

    @SuppressLint("CheckResult")
    override fun sendFCM(reservationProduct: ReservationProduct) {
        //이장님께 FCM 전송
        fcmInterface.sendNotification(
            NotificationBody(
                reservationProduct.product.fcm,
                NotificationData(
                    title = reservationProduct.product.title,
                    message = convertTimeToUserDenyFcmMessage(reservationProduct.reservation.startDateTimestamp),
                    productId = reservationProduct.product.id,
                    uuid = uuid,
                    alarmTimestamp = getTimestamp(),
                    alarmCase = ALARM_RESERVATION_REQUEST_CASE5,
                    isScheduled = "false",
                    reservationId = reservationProduct.reservation.id
                )
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { Log.d(TAG, "SUCCESS") },
                onError = { Log.d(TAG, "FAIL") }
            )
    }
}