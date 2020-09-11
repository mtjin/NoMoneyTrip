package com.mtjin.nomoneytrip.data.local_page.source

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.nomoneytrip.api.ApiInterface
import com.mtjin.nomoneytrip.data.community.Review
import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.local_page.TourIntroduce
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.utils.*
import io.reactivex.Single

class LocalPageRepositoryImpl(
    private val apiInterface: ApiInterface,
    private val database: DatabaseReference
) : LocalPageRepository {
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

    override fun requestProducts(city: String): Single<List<Product>> {
        return Single.create { emitter ->
            database.child(PRODUCT).orderByChild(CITY).equalTo(city)
                .addListenerForSingleValueEvent(object : ValueEventListener {
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
                        emitter.onSuccess(productList)
                    }

                })
        }
    }

    override fun requestReviews(city: String): Single<List<UserReview>> {
        var userList = ArrayList<User>()
        var productList = ArrayList<Product>()
        var userReviewList = ArrayList<UserReview>()
        return Single.create<List<UserReview>> { emitter ->
            database.child(PRODUCT).orderByChild(CITY).equalTo(city)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (productSnapshot in snapshot.children) {
                            productSnapshot.getValue(Product::class.java)?.let {
                                productList.add(it)
                            }
                        }
                        database.child(USER)
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onCancelled(error: DatabaseError) {
                                    emitter.onError(error.toException())
                                }

                                override fun onDataChange(snapshot: DataSnapshot) {
                                    for (userSnapshot in snapshot.children) {
                                        userSnapshot.getValue(User::class.java)?.let {
                                            userList.add(it)
                                        }
                                    }
                                    database.child(REVIEW).orderByChild(CITY).equalTo(city)
                                        .addListenerForSingleValueEvent(object :
                                            ValueEventListener {
                                            override fun onCancelled(error: DatabaseError) {
                                                emitter.onError(error.toException())
                                            }

                                            override fun onDataChange(snapshot: DataSnapshot) {
                                                for (reviewSnapshot in snapshot.children) {
                                                    reviewSnapshot.getValue(Review::class.java)
                                                        ?.let { review ->
                                                            for (product in productList) {
                                                                if (product.id == review.productId) {
                                                                    for (user in userList) {
                                                                        if (user.id == review.userId) {
                                                                            userReviewList.add(
                                                                                UserReview(
                                                                                    user = user,
                                                                                    review = review,
                                                                                    product = product
                                                                                )
                                                                            )
                                                                        }
                                                                    }
                                                                    break
                                                                }
                                                            }
                                                        }
                                                }
                                                emitter.onSuccess(userReviewList)
                                            }

                                        })
                                }

                            })
                    }

                })
        }
    }
}