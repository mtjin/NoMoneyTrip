package com.mtjin.nomoneytrip.views.login

import androidx.lifecycle.LiveData
import com.kakao.auth.Session
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.login.source.LoginRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class LoginViewModel(private val loginRepository: LoginRepository) :
    BaseViewModel() {
    private val _kakaoLogin = SingleLiveEvent<Session>()
    private val _goEmailSignUp: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _goEmailLogin: SingleLiveEvent<Unit> = SingleLiveEvent()

    val kakaoLogin: LiveData<Session> get() = _kakaoLogin
    val goEmailSignUp: LiveData<Unit> get() = _goEmailSignUp
    val goEmailLogin: LiveData<Unit> get() = _goEmailLogin

    fun kakaoLogin() {
        showProgress()
        _kakaoLogin.value = loginRepository.kakaoLogin()
    }

    fun goEmailSignUp() {
        _goEmailSignUp.call()
    }

    fun goEmailLogin() {
        _goEmailLogin.call()
    }

}