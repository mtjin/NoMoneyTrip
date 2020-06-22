package com.mtjin.nomoneytrip.views.login

import android.util.Log
import androidx.lifecycle.LiveData
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.login.source.LoginRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class LoginViewModel(private val loginRepository: LoginRepository) : BaseViewModel() {
    private val _loginSuccess = SingleLiveEvent<Any>()
    private val _exception = SingleLiveEvent<Exception>()
    private val _kakaoLogin = SingleLiveEvent<Session>()

    val loginSuccess: LiveData<Any> get() = _loginSuccess
    val exception: LiveData<Exception> get() = _exception
    val kakaoLogin: LiveData<Session> get() = _kakaoLogin

    // 세션 콜백 구현
    val sessionCallback: ISessionCallback = object : ISessionCallback {
        override fun onSessionOpened() {
            _loginSuccess.call()
        }

        override fun onSessionOpenFailed(exception: KakaoException) {
            _exception.value = exception
        }
    }

    fun kakaoLogin() {
        _kakaoLogin.value = loginRepository.kakaoLogin()
        Log.d("AAAA", "kakaoLOgin")
    }

    override fun onCleared() {
        super.onCleared()
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback)
    }
}