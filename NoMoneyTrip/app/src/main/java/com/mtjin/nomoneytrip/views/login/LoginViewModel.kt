package com.mtjin.nomoneytrip.views.login

import androidx.lifecycle.LiveData
import com.kakao.auth.Session
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.login.source.LoginRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class LoginViewModel(private val loginRepository: LoginRepository) : BaseViewModel() {
    private val _kakaoLogin = SingleLiveEvent<Session>()

    val kakaoLogin: LiveData<Session> get() = _kakaoLogin

    fun kakaoLogin() {
        _kakaoLogin.value = loginRepository.kakaoLogin()
    }

}