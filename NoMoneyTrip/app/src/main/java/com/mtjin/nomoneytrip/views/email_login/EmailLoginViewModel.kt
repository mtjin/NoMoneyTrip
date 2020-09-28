package com.mtjin.nomoneytrip.views.email_login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.email_login.source.EmailLoginRepository
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.utils.SingleLiveEvent
import com.mtjin.nomoneytrip.utils.TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class EmailLoginViewModel(private val emailLoginRepository: EmailLoginRepository) :
    BaseViewModel() {
    val email: MutableLiveData<String> = MutableLiveData("")
    val pw: MutableLiveData<String> = MutableLiveData("")
    private val _isEmailEmpty: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _isPwEmpty: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _login: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _backClick: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _loginSuccess: SingleLiveEvent<Unit> = SingleLiveEvent()

    val isEmailEmpty: LiveData<Unit> get() = _isEmailEmpty
    val isPwEmpty: LiveData<Unit> get() = _isPwEmpty
    val login: LiveData<Unit> get() = _login
    val backCLick: LiveData<Unit> get() = _backClick
    val loginSuccess: LiveData<Unit> get() = _loginSuccess

    fun onEmailLoginClick() {
        val email: String = email.value.toString().trim()
        val pw: String = pw.value.toString().trim()

        when {
            email.isEmpty() -> _isEmailEmpty.call()
            pw.isEmpty() -> _isPwEmpty.call()
            else -> {
                showProgress()
                _login.call()
            }
        }
    }

    fun insertUser(user: User) {
        compositeDisposable.add(
            emailLoginRepository.insertUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onComplete = { _loginSuccess.call() },
                    onError = { Log.d(TAG, "EmailLoginViewModel insertUser() error -> $it") }
                )
        )
    }

    fun updateFCM() {
        compositeDisposable.add(
            emailLoginRepository.updateFCM()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onComplete = { _loginSuccess.call() },
                    onError = { Log.d(TAG, "EmailLoginViewModel updateFCM() error -> $it") }
                )
        )
    }

    fun onBackButtonClick() {
        _backClick.call()
    }
}