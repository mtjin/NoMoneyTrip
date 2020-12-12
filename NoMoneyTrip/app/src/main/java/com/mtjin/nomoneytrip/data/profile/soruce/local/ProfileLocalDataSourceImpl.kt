package com.mtjin.nomoneytrip.data.profile.soruce.local

import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.community.source.local.UserReviewDao
import com.mtjin.nomoneytrip.data.login.User
import io.reactivex.Completable
import io.reactivex.Single

class ProfileLocalDataSourceImpl(
    private val userDao: UserDao,
    private val userReviewDao: UserReviewDao
) : ProfileLocalDataSource {
    override fun insertUser(user: User): Completable = userDao.insertUser(user)

    override fun getMyUserReviews(uuid: String): Single<List<UserReview>> =
        userReviewDao.getMyUserReviews(uuid)

    override fun getUser(uuid: String): Single<User> = userDao.getUser(uuid)
}