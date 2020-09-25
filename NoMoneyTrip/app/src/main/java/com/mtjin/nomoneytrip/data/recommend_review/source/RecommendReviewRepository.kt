package com.mtjin.nomoneytrip.data.recommend_review.source

import com.mtjin.nomoneytrip.data.community.UserReview
import io.reactivex.Completable
import io.reactivex.Single

interface RecommendReviewRepository {
    fun updateReviewRecommend(userReview: UserReview): Completable
    fun requestMyRecommendReviews(): Single<List<UserReview>>
}