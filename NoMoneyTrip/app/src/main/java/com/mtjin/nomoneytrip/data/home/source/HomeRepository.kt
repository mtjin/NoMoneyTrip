package com.mtjin.nomoneytrip.data.home.source

import com.mtjin.nomoneytrip.data.home.Product
import io.reactivex.Completable
import io.reactivex.Single

interface HomeRepository {
    fun requestProducts(): Single<List<Product>>
    fun requestHashTagProducts(hashTag: String): Single<List<Product>>
    fun updateProductFavorite(product: Product): Completable
}