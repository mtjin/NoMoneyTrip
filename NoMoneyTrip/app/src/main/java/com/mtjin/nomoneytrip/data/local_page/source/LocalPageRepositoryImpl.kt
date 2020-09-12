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
import io.reactivex.Completable
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

    override fun requestReviews(city: String, page: Int): Single<List<UserReview>> {
        val userList = ArrayList<User>()
        val productList = ArrayList<Product>()
        val userReviewList = ArrayList<UserReview>()
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
                                        .limitToLast(page)
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
                                                userReviewList.sortByDescending { it.review.timestamp }
                                                emitter.onSuccess(userReviewList)
                                            }

                                        })
                                }

                            })
                    }

                })
        }
    }

    override fun updateReviewRecommend(userReview: UserReview): Completable {
        return Completable.create { emitter ->
            val recommendMap = HashMap<String, Any>()
            recommendMap[RECOMMEND_LIST] = userReview.review.recommendList
            database.child(REVIEW).child(userReview.review.id).updateChildren(recommendMap)
                .addOnSuccessListener {
                    emitter.onComplete()
                }.addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

    override fun updateProductFavorite(product: Product): Completable {
        return Completable.create { emitter ->
            val updateMap = HashMap<String, Any>()
            updateMap[FAVORITE_LIST] = product.favoriteList
            database.child(PRODUCT).child(product.id).updateChildren(updateMap)
                .addOnSuccessListener {
                    database.child(FAVORITE).child(uuid).child(product.id).setValue(product.id)
                }.addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }
}