package com.mtjin.nomoneytrip.data.tour_no_history.source

import com.mtjin.nomoneytrip.data.home.Product
import io.reactivex.Single

interface TourNoHistoryRepository {
    fun requestProducts(): Single<List<Product>>
}