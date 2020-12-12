package com.mtjin.nomoneytrip.data.community.source

import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.community.source.local.CommunityLocalDataSource
import com.mtjin.nomoneytrip.data.community.source.remote.CommunityRemoteDataSource
import com.mtjin.nomoneytrip.data.reservation_history.ReservationProduct
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class CommunityRepositoryImpl(
    private val remote: CommunityRemoteDataSource,
    private val local: CommunityLocalDataSource
) : CommunityRepository {
    override fun requestMyReviews(): Single<List<ReservationProduct>> = remote.requestMyReviews()

    override fun requestReviews(cityCode: String): Flowable<List<UserReview>> {
        return local.getUserReviews()
            .observeOn(Schedulers.io())
            .onErrorReturn { listOf() }
            .flatMapPublisher { cachedItems ->
                if (cachedItems.isEmpty()) {
                    requestRemoteReviews(cityCode).toFlowable().onErrorReturn { listOf() }
                } else {
                    val local = Single.just(cachedItems)
                    val remote = requestRemoteReviews(cityCode).onErrorResumeNext { local }
                    Single.concat(local, remote)
                }
            }
    }

    override fun requestRemoteReviews(cityCode: String): Single<List<UserReview>> {
        return remote.requestReviews(cityCode).observeOn(Schedulers.io())
            .flatMap {
                local.insertUserReviews(it)
                    .andThen(Single.just(it))
            }
    }

    override fun updateReviewRecommend(userReview: UserReview): Completable =
        remote.updateReviewRecommend(userReview)


}