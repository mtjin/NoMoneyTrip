package com.mtjin.nomoneytrip.data.community.source.local

import com.mtjin.nomoneytrip.data.community.UserReview
import io.reactivex.Completable
import io.reactivex.Single

interface CommunityLocalDataSource {
    fun insertUserReviews(userReviews: List<UserReview>): Completable
    fun getUserReviews(): Single<List<UserReview>>
}