package com.mtjin.nomoneytrip.data.community.source.local

import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.reservation_history.ReservationProduct
import io.reactivex.Completable
import io.reactivex.Single

class CommunityLocalDataSourceImpl(
    private val userReviewDao: UserReviewDao
) : CommunityLocalDataSource {

    override fun insertUserReviews(userReviews: List<UserReview>): Completable =
        userReviewDao.insertUserReviews(userReviews)

    override fun getUserReviews(): Single<List<UserReview>> = userReviewDao.getUserReviews()
}