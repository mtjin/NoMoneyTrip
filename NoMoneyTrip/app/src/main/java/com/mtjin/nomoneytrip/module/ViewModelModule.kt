package com.mtjin.nomoneytrip.module

import com.mtjin.nomoneytrip.views.alarm.AlarmViewModel
import com.mtjin.nomoneytrip.views.email_login.EmailLoginViewModel
import com.mtjin.nomoneytrip.views.email_signup.EmailSignUpViewModel
import com.mtjin.nomoneytrip.views.home.HomeViewModel
import com.mtjin.nomoneytrip.views.localpage.LocalPageViewModel
import com.mtjin.nomoneytrip.views.login.LoginViewModel
import com.mtjin.nomoneytrip.views.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelModule: Module = module {
    viewModel { LoginViewModel(get()) }
    viewModel { EmailSignUpViewModel() }
    viewModel { EmailLoginViewModel(get()) }
    viewModel { HomeViewModel() }
    viewModel { AlarmViewModel() }
    viewModel { SearchViewModel() }
    viewModel { LocalPageViewModel(get()) }
}