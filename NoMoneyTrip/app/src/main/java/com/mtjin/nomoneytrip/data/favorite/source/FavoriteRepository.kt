package com.mtjin.nomoneytrip.data.favorite.source

import com.mtjin.nomoneytrip.data.home.Product
import io.reactivex.Single

interface FavoriteRepository {
    fun requestFavorites(): Single<List<Product>>
}