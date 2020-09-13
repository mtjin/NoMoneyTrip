package com.mtjin.nomoneytrip.data.tour_write.source

import android.net.Uri
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.mtjin.nomoneytrip.data.community.Review
import com.mtjin.nomoneytrip.data.reservation_history.ReservationProduct
import com.mtjin.nomoneytrip.utils.*
import io.reactivex.Completable


class TourWriteRepositoryImpl(
    private val database: DatabaseReference,
    private val storage: StorageReference
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

}