package com.mtjin.nomoneytrip.module

import androidx.room.Room
import com.mtjin.nomoneytrip.data.community.source.local.CommunityLocalDataSource
import com.mtjin.nomoneytrip.data.community.source.local.CommunityLocalDataSourceImpl
import com.mtjin.nomoneytrip.data.community.source.local.UserReviewDao
import com.mtjin.nomoneytrip.data.database.MyRoomDatabase
import com.mtjin.nomoneytrip.data.login.source.local.LoginLocalDataSource
import com.mtjin.nomoneytrip.data.login.source.local.LoginLocalDataSourceImpl
import com.mtjin.nomoneytrip.utils.PreferenceManager
import org.koin.core.module.Module
import org.koin.dsl.module

val localDataModule: Module = module {
    //SharedPref
    single<PreferenceManager> { PreferenceManager(get()) }
    //Database
    single<MyRoomDatabase> {
        Room.databaseBuilder(
            get(),
            MyRoomDatabase::class.java, "NoMoneyTrip.db"
        ).build()
    }
    //LocalDataSource
    single<LoginLocalDataSource> { LoginLocalDataSourceImpl() }
    single<CommunityLocalDataSource> { CommunityLocalDataSourceImpl(get()) }
    //Dao
    single<UserReviewDao> { get<MyRoomDatabase>().userReviewDao() }
}