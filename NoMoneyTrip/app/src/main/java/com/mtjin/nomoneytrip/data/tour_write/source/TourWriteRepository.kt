package com.mtjin.nomoneytrip.data.tour_write.source

import android.net.Uri
import com.mtjin.nomoneytrip.data.community.Review
import com.mtjin.nomoneytrip.data.reservation_history.ReservationProduct
import io.reactivex.Completable

interface TourWriteRepository {
    fun insertReview(
        imageUri: Uri,
        reservationProduct: ReservationProduct,
        review: Review
    ): Completable
}