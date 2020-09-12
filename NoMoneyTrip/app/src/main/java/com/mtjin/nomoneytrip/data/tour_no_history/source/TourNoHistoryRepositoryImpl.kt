package com.mtjin.nomoneytrip.data.tour_no_history.source

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.utils.PRODUCT
import io.reactivex.Single
import java.util.*
import kotlin.collections.ArrayList

class TourNoHistoryRepositoryImpl(private val database: DatabaseReference) :
    TourNoHistoryRepository {
    override fun requestProducts(): Single<List<Product>> {
        return Single.create<List<Product>> { emitter ->
            database.child(PRODUCT).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    emitter.onError(error.toException())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val productList = ArrayList<Product>()
                    for (productSnapShot: DataSnapshot in snapshot.children) {
                        productSnapShot.getValue(Product::class.java)?.let {
                            productList.add(it)
                        }
                    }
                    val seed = System.currentTimeMillis()
                    productList.shuffle(Random(seed))
                    emitter.onSuccess(productList)
                }

            })
        }

    }
}