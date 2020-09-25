package com.mtjin.nomoneytrip.data.favorite.source

import com.mtjin.nomoneytrip.data.home.Product
import io.reactivex.Flowable

interface FavoriteRepository {
    fun requestFavorites(): Flowable<List<Product>>
}