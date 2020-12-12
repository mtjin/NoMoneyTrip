package com.mtjin.nomoneytrip.data.community.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.reservation_history.ReservationProduct
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserReviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserReviews(userReviews: List<UserReview>): Completable

    @Query("SELECT * FROM userReview")
    fun getUserReviews(): Single<List<UserReview>>
}