package com.mtjin.nomoneytrip.views.phone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.phone.source.PhoneAuthRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class PhoneAuthViewModel(private val repository: PhoneAuthRepository) : BaseViewModel() {
    lateinit var authNum: String //문자로온 인증번호
    var tel: String = ""
    val etPhoneNum = MutableLiveData<String>("")
    val etAuthNum = MutableLiveData<String>("")
    private val _requestAuth = SingleLiveEvent<Boolean>()
    private val _authState = MutableLiveData<Boolean>() //인증번호 요청 했는지 유무
    private val _resultAuthUser = MutableLiveData<Boolean>()

    val requestAuth: LiveData<Boolean> get() = _requestAuth
    val authState: LiveData<Boolean> get() = _authState
    val resultAuthUser: LiveData<Boolean> get() = _resultAuthUser

    fun requestAuth() {
        _requestAuth.value = !etPhoneNum.value.isNullOrBlank()
    }

    fun updateAuthState(boolean: Boolean) {
        _authState.value = boolean
        tel = etPhoneNum.value.toString()
    }

    fun authUser() {
        if (this::authNum.isInitialized && authNum == etAuthNum.value.toString()) {
            updateUserTel()
        } else {
            _resultAuthUser.value = false
        }
    }

    private fun updateUserTel() {
        compositeDisposable.add(
            repository.updateUserTel(tel = tel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doAfterTerminate { hideProgress() }
                .subscribeBy(
                    onError = { _resultAuthUser.value = false },
                    onComplete = { _resultAuthUser.value = true }
                )
        )
    }
}