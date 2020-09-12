package com.mtjin.nomoneytrip.data.lodgment_detail.source

import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.local_page.TourIntroduce
import io.reactivex.Completable
import io.reactivex.Single

interface LodgmentDetailRepository {
    fun requestReviews(productId: String, page : Int) : Single<List<UserReview>>
    fun updateReviewRecommend(userReview: UserReview): Completable
    fun updateProductFavorite(product: Product): Completable
}