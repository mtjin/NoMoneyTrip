package com.mtjin.nomoneytrip.module

import com.mtjin.nomoneytrip.data.login.source.LoginRepository
import com.mtjin.nomoneytrip.data.login.source.LoginRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    single<LoginRepository> {LoginRepositoryImpl(get())}
}