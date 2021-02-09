package com.mtjin.nomoneytrip.data.tour_write.source

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.mtjin.nomoneytrip.api.FcmInterface
import com.mtjin.nomoneytrip.data.community.Review
import com.mtjin.nomoneytrip.data.reservation_history.ReservationProduct
import com.mtjin.nomoneytrip.service.notification.NotificationBody
import com.mtjin.nomoneytrip.service.notification.NotificationData
import com.mtjin.nomoneytrip.utils.*
import com.mtjin.nomoneytrip.utils.extensions.getTimestamp
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers


class TourWriteRepositoryImpl(
    private val database: DatabaseReference,
    private val storage: StorageReference,
    private val fcmInterface: FcmInterface
) : TourWriteRepository {
    override fun insertReview(
        imageUri: Uri,
        reservationProduct: ReservationProduct,
        review: Review
    ): Completable {
        return Completable.create { emitter ->
            val storageRef = storage.child(getTimestamp().toString() + uuid + ".png")
            val uploadTask = storageRef.putFile(imageUri)
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                storageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result.toString()
                    val key = database.push().key.toString()
                    review.image = downloadUri
                    review.id = key
                    database.child(REVIEW).child(key).setValue(review)
                        .addOnSuccessListener {
                            val updateReviewMap = hashMapOf<String, Any>(
                                REVIEWED to true
                            )
                            val ratingMap = hashMapOf<String, Any>(
                                RATING_LIST to reservationProduct.product.ratingList
                            )
                            database.child(RESERVATION).child(reservationProduct.reservation.id)
                                .updateChildren(updateReviewMap).addOnSuccessListener {
                                    database.child(PRODUCT).child(reservationProduct.product.id)
                                        .updateChildren(ratingMap)
                                    sendFCM(reservationProduct = reservationProduct)
                                    emitter.onComplete()
                                }.addOnFailureListener {
                                    emitter.onError(it)
                                }
                        }.addOnFailureListener {
                            emitter.onError(it)
                        }
                } else {
                    Log.d(
                        TAG, task.exception.toString()
                    )
                    emitter.onError(Throwable(ERR_UPLOAD_IMAGE))
                }
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
                    message = "사용자가 리뷰를 작성했습니다. :)",
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