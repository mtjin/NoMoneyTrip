package com.mtjin.nomoneytrip.data.profile.soruce.local

import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.login.User
import io.reactivex.Completable
import io.reactivex.Single

interface ProfileLocalDataSource {
    fun insertUser(user: User): Completable
    fun getMyUserReviews(uuid: String): Single<List<UserReview>>
    fun getUser(uuid: String): Single<User>
}