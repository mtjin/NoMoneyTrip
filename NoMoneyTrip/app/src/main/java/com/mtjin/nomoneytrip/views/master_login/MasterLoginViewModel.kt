package com.mtjin.nomoneytrip.views.master_login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.master_login.source.MasterLoginRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent
import com.mtjin.nomoneytrip.utils.TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MasterLoginViewModel(private val repository: MasterLoginRepository) : BaseViewModel() {
    val email: MutableLiveData<String> = MutableLiveData("")
    val pw: MutableLiveData<String> = MutableLiveData("")
    private val _isEmailEmpty: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _isPwEmpty: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _login: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _backClick: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _loginSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()

    val isEmailEmpty: LiveData<Unit> get() = _isEmailEmpty
    val isPwEmpty: LiveData<Unit> get() = _isPwEmpty
    val login: LiveData<Unit> get() = _login
    val backCLick: LiveData<Unit> get() = _backClick
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess

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

    fun requestIdPw() {
        email.value = repository.masterIdInput
        pw.value = repository.masterPwInput
    }

    fun updateMasterFCM() {
        repository.updateFCM()
    }

    fun requestMasterLogin() {
        compositeDisposable.add(
            repository.requestMasterLogin(id = email.value.toString(), pw = pw.value.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doAfterTerminate { hideProgress() }
                .subscribeBy(
                    onComplete = { _loginSuccess.value = true },
                    onError = {
                        _loginSuccess.value = false
                        Log.d(
                            TAG,
                            "MasterLoginViewModel requestMasterLogin() error -> $it"
                        )
                    }
                )
        )
    }

    fun onBackButtonClick() {
        _backClick.call()
    }
}