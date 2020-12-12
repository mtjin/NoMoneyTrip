package com.mtjin.nomoneytrip.module

import com.mtjin.nomoneytrip.data.community.source.remote.CommunityRemoteDataSource
import com.mtjin.nomoneytrip.data.community.source.remote.CommunityRemoteDataSourceImpl
import com.mtjin.nomoneytrip.data.login.source.remote.LoginRemoteDataSource
import com.mtjin.nomoneytrip.data.login.source.remote.LoginRemoteDataSourceImpl
import com.mtjin.nomoneytrip.data.profile.soruce.remote.ProfileRemoteDataSource
import com.mtjin.nomoneytrip.data.profile.soruce.remote.ProfileRemoteDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val remoteDataModule: Module = module {
    single<LoginRemoteDataSource> { LoginRemoteDataSourceImpl(get()) }
    single<CommunityRemoteDataSource> { CommunityRemoteDataSourceImpl(get()) }
    single<ProfileRemoteDataSource> { ProfileRemoteDataSourceImpl(get()) }
}