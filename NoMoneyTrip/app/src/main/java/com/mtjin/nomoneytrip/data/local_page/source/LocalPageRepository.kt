package com.mtjin.nomoneytrip.data.local_page.source

import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.local_page.TourIntroduce
import io.reactivex.Completable
import io.reactivex.Single

interface LocalPageRepository {
    fun requestTourIntroduces(areaCode: Int): Single<List<TourIntroduce>>
    fun requestRestaurantIntroduces(areaCode: Int): Single<List<TourIntroduce>>
    fun requestProducts(city: String): Single<List<Product>>
    fun requestReviews(city: String, page: Int): Single<List<UserReview>>
    fun updateReviewRecommend(userReview: UserReview): Completable
}