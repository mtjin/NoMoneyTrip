package com.mtjin.nomoneytrip.data.favorite.source

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.utils.PRODUCT
import com.mtjin.nomoneytrip.utils.uuid
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

class FavoriteRepositoryImpl(private val database: DatabaseReference) : FavoriteRepository {
    override fun requestFavorites(): Flowable<List<Product>> {
        return Flowable.create({ emitter ->
            database.child(PRODUCT)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val productList = ArrayList<Product>()
                        for (productSnapShot: DataSnapshot in snapshot.children) {
                            productSnapShot.getValue(Product::class.java)?.let {
                                if (it.favoriteList.contains(uuid)) {
                                    productList.add(it)
                                }
                            }
                        }
                        emitter.onNext(productList)
                    }

                })
        }, BackpressureStrategy.BUFFER)
    }
}