package com.mtjin.nomoneytrip.data.profile.soruce

import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.data.master_write.MasterLetter
import com.mtjin.nomoneytrip.data.profile.soruce.local.ProfileLocalDataSource
import com.mtjin.nomoneytrip.data.profile.soruce.remote.ProfileRemoteDataSource
import com.mtjin.nomoneytrip.utils.uuid
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ProfileRepositoryImpl(
    private val local: ProfileLocalDataSource,
    private val remote: ProfileRemoteDataSource
) : ProfileRepository {
    override fun requestProfile(): Flowable<User> {
        return local.getUser(uuid = uuid)
            .observeOn(Schedulers.io())
            .onErrorReturn { User() }
            .flatMapPublisher { cachedUser ->
                val local = Single.just(cachedUser)
                val remote = requestRemoteProfile().onErrorResumeNext { local }
                Single.concat(local, remote)
            }
    }

    override fun requestRemoteProfile(): Single<User> {
        return remote.requestProfile()
            .observeOn(Schedulers.io())
            .flatMap { user ->
                local.insertUser(user).andThen(Single.just(user))
            }
    }

    override fun updateReviewRecommend(userReview: UserReview): Completable =
        remote.updateReviewRecommend(userReview)

    override fun requestMyReviews(): Flowable<List<UserReview>> {
        return local.getMyUserReviews(uuid = uuid)
            .observeOn(Schedulers.io())
            .onErrorReturn { listOf() }
            .flatMapPublisher { cachedItems ->
                if (cachedItems.isEmpty()) {
                    requestRemoteMyReviews().toFlowable().onErrorReturn { listOf() }
                } else {
                    val local = Single.just(cachedItems)
                    val remote = requestRemoteMyReviews().onErrorResumeNext { local }
                    Single.concat(local, remote)
                }
            }
    }

    override fun requestRemoteMyReviews(): Single<List<UserReview>> =
        remote.requestMyReviews().observeOn(Schedulers.io())


    override fun requestFavorites(): Single<List<Product>> = remote.requestFavorites()

    override fun updateProductFavorite(product: Product): Completable =
        remote.updateProductFavorite(product)

    override fun requestMasterLetters(): Single<List<MasterLetter>> = remote.requestMasterLetters()

}
