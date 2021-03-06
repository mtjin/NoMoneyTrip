package com.mtjin.nomoneytrip.data.community.source.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.nomoneytrip.data.community.Review
import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.data.reservation.Reservation
import com.mtjin.nomoneytrip.data.reservation_history.ReservationProduct
import com.mtjin.nomoneytrip.utils.*
import io.reactivex.Completable
import io.reactivex.Single

class CommunityRemoteDataSourceImpl(private val database: DatabaseReference) :
    CommunityRemoteDataSource {
    override fun requestMyReviews(): Single<List<ReservationProduct>> {
        return Single.create<List<ReservationProduct>> { emitter ->
            val productList = ArrayList<Product>()
            database.child(PRODUCT).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    emitter.onError(error.toException())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    productList.clear()
                    for (snapshot2: DataSnapshot in snapshot.children) {
                        snapshot2.getValue(Product::class.java)?.let {
                            productList.add(it)
                        }
                    }
                    database.child(RESERVATION).orderByChild(USER_ID).equalTo(uuid)
                        .addValueEventListener(object : ValueEventListener {
                            override fun onCancelled(error: DatabaseError) {
                                emitter.onError(error.toException())
                            }

                            override fun onDataChange(snapshot: DataSnapshot) {
                                val list = ArrayList<ReservationProduct>()
                                for (reserveSnapShot: DataSnapshot in snapshot.children) {
                                    reserveSnapShot.getValue(Reservation::class.java)?.let {
                                        for (product in productList) {
                                            if (product.id == it.productId) {
                                                list.add(
                                                    ReservationProduct(
                                                        id = it.id,
                                                        reservation = it,
                                                        product = product
                                                    )
                                                )
                                                break
                                            }
                                        }
                                    }
                                }
                                emitter.onSuccess(list)
                            }
                        })
                }
            })
        }
    }

    override fun requestReviews(cityCode: String): Single<List<UserReview>> {
        var userList = ArrayList<User>()
        var productList = ArrayList<Product>()
        var userReviewList = ArrayList<UserReview>()
        if (cityCode == "국내 전체") {//국내전체
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
                                                                                    id = user.id + review.id,
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
        } else {
            return Single.create<List<UserReview>> { emitter ->
                database.child(PRODUCT).orderByChild(CITY).equalTo(cityCode)
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
                                        database.child(REVIEW)
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
                                                                                        id = user.id + review.id,
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
}