package com.mtjin.nomoneytrip.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.community.source.local.UserReviewDao
import com.mtjin.nomoneytrip.data.database.type_converter.MyTypeConverters

@Database(
    entities = [UserReview::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MyTypeConverters::class)
abstract class MyRoomDatabase : RoomDatabase() {
    abstract fun userReviewDao(): UserReviewDao
}