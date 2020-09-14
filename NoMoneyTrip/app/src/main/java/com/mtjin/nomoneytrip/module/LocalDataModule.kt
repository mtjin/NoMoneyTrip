package com.mtjin.nomoneytrip.module

import com.mtjin.nomoneytrip.data.login.source.local.LoginLocalDataSource
import com.mtjin.nomoneytrip.data.login.source.local.LoginLocalDataSourceImpl
import com.mtjin.nomoneytrip.utils.PreferenceManager
import org.koin.core.module.Module
import org.koin.dsl.module

val localDataModule: Module = module {
    single<LoginLocalDataSource> { LoginLocalDataSourceImpl() }
    single<PreferenceManager> { PreferenceManager(get()) }
}