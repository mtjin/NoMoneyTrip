package com.mtjin.nomoneytrip.data.local_page.source

import com.mtjin.nomoneytrip.data.local_page.TourIntroduce
import com.mtjin.nomoneytrip.data.local_page.TourIntroduceResponseBody
import io.reactivex.Single

interface LocalPageRepository {
    fun requestTourIntroduces(areaCode: Int): Single<List<TourIntroduce>>
    fun requestRestaurantIntroduces(areaCode: Int): Single<List<TourIntroduce>>
}