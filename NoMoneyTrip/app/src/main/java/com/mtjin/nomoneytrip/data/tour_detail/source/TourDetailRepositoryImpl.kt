package com.mtjin.nomoneytrip.data.tour_detail.source

import com.mtjin.nomoneytrip.api.ApiInterface
import com.mtjin.nomoneytrip.data.local_page.TourIntroduce
import com.mtjin.nomoneytrip.data.tour_detail.TourProductDetailBody
import io.reactivex.Single

class TourDetailRepositoryImpl(private val apiInterface: ApiInterface) : TourDetailRepository {
    override fun requestTourProductDetails(tourIntroduce: TourIntroduce): Single<TourProductDetailBody> {
        return apiInterface.getTourProductDetail(
            contentTypeId = tourIntroduce.contenttypeid,
            contentId = tourIntroduce.contentid
        )
    }
}