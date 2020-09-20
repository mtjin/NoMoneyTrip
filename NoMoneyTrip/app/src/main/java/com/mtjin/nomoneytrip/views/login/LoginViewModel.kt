package com.mtjin.nomoneytrip.views.login

import androidx.lifecycle.LiveData
import com.kakao.auth.Session
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.data.login.source.LoginRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class LoginViewModel(private val loginRepository: LoginRepository) :
    BaseViewModel() {
    private val _kakaoLogin = SingleLiveEvent<Session>()
    private val _goEmailSignUp: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _goEmailLogin: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _insertUserResult: SingleLiveEvent<Boolean> = SingleLiveEvent()

    val kakaoLogin: LiveData<Session> get() = _kakaoLogin
    val goEmailSignUp: LiveData<Unit> get() = _goEmailSignUp
    val goEmailLogin: LiveData<Unit> get() = _goEmailLogin
    val insertUserResult: LiveData<Boolean> get() = _insertUserResult

    fun kakaoLogin() {
        showProgress()
        _kakaoLogin.value = loginRepository.kakaoLogin()
    }

    fun goEmailSignUp() {
        _goEmailSignUp.call()
    }

    fun insertUser(user: User) {
        compositeDisposable.add(
            loginRepository.insertUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onComplete = { _insertUserResult.value = true },
                    onError = { _insertUserResult.value = false }
                )
        )
    }

    fun goEmailLogin() {
        _goEmailLogin.call()
    }

    fun updateFCM() {
        loginRepository.updateFCM()
    }

}