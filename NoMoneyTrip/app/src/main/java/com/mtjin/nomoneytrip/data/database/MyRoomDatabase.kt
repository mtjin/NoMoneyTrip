package com.mtjin.nomoneytrip.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.community.source.local.UserReviewDao
import com.mtjin.nomoneytrip.data.database.type_converter.MyTypeConverters
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.data.profile.soruce.local.UserDao

@Database(
    entities = [UserReview::class, User::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MyTypeConverters::class)
abstract class MyRoomDatabase : RoomDatabase() {
    abstract fun userReviewDao(): UserReviewDao
    abstract fun userDao(): UserDao
}