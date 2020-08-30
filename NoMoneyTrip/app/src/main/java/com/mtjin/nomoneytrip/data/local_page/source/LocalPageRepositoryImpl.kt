package com.mtjin.nomoneytrip.data.local_page.source

import android.util.Log
import com.mtjin.nomoneytrip.api.ApiInterface
import com.mtjin.nomoneytrip.data.local_page.TourIntroduce
import com.mtjin.nomoneytrip.utils.TAG
import io.reactivex.Single

class LocalPageRepositoryImpl(private val apiInterface: ApiInterface) : LocalPageRepository {
    override fun requestTourIntroduces(areaCode: Int): Single<List<TourIntroduce>> =
        apiInterface.getTourIntroduce(areaCode = areaCode).flatMap {
            Log.d(
                TAG,
                "LocalPageRepositoryImpl requestTourIntroduces() -> " + it.tourIntroduceResponse.tourIntroduceHeader.resultMsg
            )
            Single.just(it.tourIntroduceResponse.tourIntroduceBody.tourIntroduceList.tourIntroduce)
        }


    override fun requestRestaurantIntroduces(areaCode: Int): Single<List<TourIntroduce>> =
        apiInterface.getRestaurantIntroduce(areaCode = areaCode).flatMap {
            Log.d(
                TAG,
                "LocalPageRepositoryImpl requestRestaurantIntroduces() -> " + it.tourIntroduceResponse.tourIntroduceHeader.resultMsg
            )
            Single.just(it.tourIntroduceResponse.tourIntroduceBody.tourIntroduceList.tourIntroduce)
        }
}