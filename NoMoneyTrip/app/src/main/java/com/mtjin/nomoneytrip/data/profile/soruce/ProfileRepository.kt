package com.mtjin.nomoneytrip.data.profile.soruce

import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.data.master_write.MasterLetter
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface ProfileRepository {
    fun requestProfile(): Flowable<User>
    fun requestRemoteProfile(): Single<User>
    fun updateReviewRecommend(userReview: UserReview): Completable
    fun requestMyReviews(): Flowable<List<UserReview>>
    fun requestRemoteMyReviews(): Single<List<UserReview>>
    fun requestFavorites(): Single<List<Product>>
    fun updateProductFavorite(product: Product): Completable
    fun requestMasterLetters(): Single<List<MasterLetter>>
}