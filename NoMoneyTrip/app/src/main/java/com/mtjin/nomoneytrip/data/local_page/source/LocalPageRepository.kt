package com.mtjin.nomoneytrip.data.local_page.source

import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.local_page.TourIntroduce
import io.reactivex.Single

interface LocalPageRepository {
    fun requestTourIntroduces(areaCode: Int): Single<List<TourIntroduce>>
    fun requestRestaurantIntroduces(areaCode: Int): Single<List<TourIntroduce>>
    fun requestProducts(city: String): Single<List<Product>>
}