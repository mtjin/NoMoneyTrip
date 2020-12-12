package com.mtjin.nomoneytrip.data.community.source.remote

import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.reservation_history.ReservationProduct
import io.reactivex.Completable
import io.reactivex.Single

interface CommunityRemoteDataSource {
    fun requestMyReviews(): Single<List<ReservationProduct>>
    fun requestReviews(cityCode: String): Single<List<UserReview>>
    fun updateReviewRecommend(userReview: UserReview): Completable
}