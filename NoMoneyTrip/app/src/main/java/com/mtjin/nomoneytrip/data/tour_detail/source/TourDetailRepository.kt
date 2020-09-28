package com.mtjin.nomoneytrip.data.tour_detail.source

import com.mtjin.nomoneytrip.data.local_page.TourIntroduce
import com.mtjin.nomoneytrip.data.tour_detail.TourProductDetailBody
import io.reactivex.Single

interface TourDetailRepository {
    fun requestTourProductDetails(tourIntroduce: TourIntroduce): Single<TourProductDetailBody>
}