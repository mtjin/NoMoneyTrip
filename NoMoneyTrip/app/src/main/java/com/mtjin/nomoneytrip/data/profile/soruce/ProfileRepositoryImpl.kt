package com.mtjin.nomoneytrip.data.profile.soruce

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.nomoneytrip.data.community.Review
import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.utils.*
import io.reactivex.Completable
import io.reactivex.Single

class ProfileRepositoryImpl(private val database: DatabaseReference) : ProfileRepository {
    override fun requestProfile(): Single<User> {
        return Single.create<User> { emitter ->
            database.child(USER).child(uuid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.getValue(User::class.java)?.let {
                            emitter.onSuccess(it)
                        }
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

    override fun requestMyReviews(): Single<List<UserReview>> {
        var user = User()
        val productList = ArrayList<Product>()
        val userReviewList = ArrayList<UserReview>()
        return Single.create<List<UserReview>> { emitter ->
            database.child(PRODUCT).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    emitter.onError(error.toException())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (productSnapshot in snapshot.children) {
                        productSnapshot.getValue(Product::class.java)?.let {
                            productList.add(it)
                        }
                    }
                    database.child(USER).child(uuid)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(error: DatabaseError) {
                                emitter.onError(error.toException())
                            }

                            override fun onDataChange(snapshot: DataSnapshot) {
                                snapshot.getValue(User::class.java)?.let { userSnapshot ->
                                    user = userSnapshot
                                }
                                database.child(REVIEW)
                                    .addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onCancelled(error: DatabaseError) {
                                            emitter.onError(error.toException())
                                        }

                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            for (reviewSnapshot in snapshot.children) {
                                                reviewSnapshot.getValue(Review::class.java)
                                                    ?.let { review ->
                                                        for (product in productList) {
                                                            if (product.id == review.productId) {
                                                                if (user.id == review.userId) {
                                                                    userReviewList.add(
                                                                        UserReview(
                                                                            user = user,
                                                                            review = review,
                                                                            product = product
                                                                        )
                                                                    )
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

    override fun requestMyRecommendReviews(): Single<List<UserReview>> {
        var userList = ArrayList<User>()
        var productList = ArrayList<Product>()
        var userReviewList = ArrayList<UserReview>()
        return Single.create<List<UserReview>> { emitter ->
            database.child(PRODUCT).addListenerForSingleValueEvent(object : ValueEventListener {
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
                                database.child(REVIEW)
                                    .addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onCancelled(error: DatabaseError) {
                                            emitter.onError(error.toException())
                                        }

                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            for (reviewSnapshot in snapshot.children) {
                                                reviewSnapshot.getValue(Review::class.java)
                                                    ?.let { review ->
                                                        if (review.recommendList.contains(uuid)) {
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
}
