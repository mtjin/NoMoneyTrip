package com.mtjin.nomoneytrip.module

import com.mtjin.nomoneytrip.data.local_page.source.LocalPageRepository
import com.mtjin.nomoneytrip.data.local_page.source.LocalPageRepositoryImpl
import com.mtjin.nomoneytrip.data.login.source.LoginRepository
import com.mtjin.nomoneytrip.data.login.source.LoginRepositoryImpl
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule: Module = module {
    single<LoginRepository> { LoginRepositoryImpl(get()) }
    single<LocalPageRepository> { LocalPageRepositoryImpl(get(named("tour"))) }
}