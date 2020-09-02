package com.mtjin.nomoneytrip.data.home.source

import com.mtjin.nomoneytrip.data.home.Product
import io.reactivex.Single

interface HomeRepository {
    fun requestProducts(): Single<List<Product>>
    fun requestHashTagProducts(hashTag: String): Single<List<Product>>
}