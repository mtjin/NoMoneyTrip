package com.mtjin.nomoneytrip.data.profile.soruce

import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.login.User
import io.reactivex.Completable
import io.reactivex.Single

interface ProfileRepository {
    fun requestProfile(): Single<User>
    fun updateReviewRecommend(userReview: UserReview): Completable
    fun requestReviews(): Single<List<UserReview>>
}