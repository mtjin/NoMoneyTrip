package com.mtjin.nomoneytrip.data.master_main.source

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.nomoneytrip.api.FcmInterface
import com.mtjin.nomoneytrip.data.alarm.Alarm
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.data.master_main.MasterProduct
import com.mtjin.nomoneytrip.data.reservation.Reservation
import com.mtjin.nomoneytrip.service.NotificationBody
import com.mtjin.nomoneytrip.service.NotificationData
import com.mtjin.nomoneytrip.utils.*
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MasterMainRepositoryImpl(
    private val database: DatabaseReference,
    private val fcmInterface: FcmInterface
) : MasterMainRepository {
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
                                if (it.state == 0) {
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
                                            if (user.id == reservation.userId && reservation.endDateTimestamp >= getTimestamp() - TimeUnit.DAYS.toMillis(
                                                    30
                                                )
                                            ) {
                                                masterProductList.add(
                                                    MasterProduct(
                                                        reservation = reservation,
                                                        user = user
                                                    )
                                                )
                                            }
                                        }
                                    }
                                    masterProductList.sortBy { it.reservation.startDateTimestamp }
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
                                if (it.state == 2) {
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
                                            if (user.id == reservation.userId && reservation.endDateTimestamp >= getTimestamp() - TimeUnit.DAYS.toMillis(
                                                    30
                                                )
                                            ) {
                                                masterProductList.add(
                                                    MasterProduct(
                                                        reservation = reservation,
                                                        user = user
                                                    )
                                                )
                                            }
                                        }
                                    }
                                    masterProductList.sortBy { it.reservation.startDateTimestamp }
                                    emitter.onSuccess(masterProductList)
                                }

                            })
                    }

                })
        }
    }

    override fun updateReservationState(
        masterProduct: MasterProduct,
        masterState: Int
    ): Completable {
        return Completable.create { emitter ->
            val masterStateMap = HashMap<String, Any>()
            masterStateMap[STATE] = masterState
            database.child(RESERVATION).child(masterProduct.reservation.id)
                .updateChildren(masterStateMap).addOnSuccessListener {
                    sendFCM(masterProduct, masterState)
                    emitter.onComplete()
                }.addOnFailureListener {
                    emitter.onError(it)
                }

        }
    }

    override fun sendFCM(masterProduct: MasterProduct, masterState: Int) {
        database.child(PRODUCT).child(masterProductId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.getValue(Product::class.java)?.let { product ->
                        var message = ""
                        if (masterState == 1) { //이장님 예약거절
                            message = convertTimeToMasterDenyFcmMessage(
                                date = masterProduct.reservation.startDateTimestamp,
                                time = product.checkIn
                            )
                        } else if (masterState == 2) { //이장님 예약수락
                            message = convertTimeToMasterAcceptFcmMessage(
                                date = masterProduct.reservation.startDateTimestamp,
                                time = product.checkIn
                            )
                        }
                        Log.d("FCM", masterProduct.user.fcm)
                        fcmInterface.sendNotification(
                            NotificationBody(
                                masterProduct.user.fcm,
                                NotificationData(
                                    title = product.title,
                                    message = message,
                                    productId = product.id,
                                    uuid = uuid,
                                    alarmTimestamp = getTimestamp(),
                                    alarmCase = ALARM_RESERVATION_REQUEST_CASE5,
                                    isScheduled = "false",
                                    reservationId = masterProduct.reservation.id
                                )
                            )
                        ).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeBy(
                                onSuccess = { Log.d(TAG, "SUCCESS") },
                                onError = { Log.d(TAG, "FAIL") }
                            )
                        var content = ""
                        var case = 0
                        if (masterState == 2) { //수락
                            content = product.title + " 예약을 이장님이 수락했습니다."
                            case = ALARM_RESERVATION_ACCEPT_CASE2
                        } else if (masterState == 1) {// 거절
                            content = product.title + " 예약을 이장님이 거절했습니다."
                            case = ALARM_RESERVATION_DENY_CASE5
                        }
                        val dbKey = database.push().key.toString()
                        database.child(ALARM).child(masterProduct.reservation.userId).child(dbKey)
                            .setValue(
                                Alarm(
                                    id = dbKey,
                                    productId = masterProduct.reservation.productId,
                                    userId = uuid,
                                    case = case,
                                    readState = false,
                                    content = content,
                                    timestamp = getTimestamp()
                                )
                            )
                    }
                }

            })
    }
}