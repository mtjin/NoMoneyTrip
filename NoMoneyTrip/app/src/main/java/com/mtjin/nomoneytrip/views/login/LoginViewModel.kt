package com.mtjin.nomoneytrip.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kakao.auth.Session
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.login.source.LoginRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class LoginViewModel(private val loginRepository: LoginRepository) :
    BaseViewModel() {
    private val _kakaoLogin = SingleLiveEvent<Session>()
    private val _goEmailSignUp: MutableLiveData<Unit> = MutableLiveData()
    private val _goEmailLogin: MutableLiveData<Unit> = MutableLiveData()

    val kakaoLogin: LiveData<Session> get() = _kakaoLogin
    val goEmailSignUp: LiveData<Unit> get() = _goEmailSignUp
    val goEmailLogin: LiveData<Unit> get() = _goEmailLogin

    fun kakaoLogin() {
        showProgress()
        _kakaoLogin.value = loginRepository.kakaoLogin()
    }

    fun goEmailSignUp() {
        _goEmailSignUp.value = Unit
    }

    fun goEmailLogin() {
        _goEmailLogin.value = Unit
    }

}