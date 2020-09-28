package com.mtjin.nomoneytrip.views.email_signup

import android.os.SystemClock
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


class EmailSignUpViewModel(private val repository: EmailLoginRepository) : BaseViewModel() {
    val email: MutableLiveData<String> = MutableLiveData("")
    val pw: MutableLiveData<String> = MutableLiveData("")
    val pwConfirm: MutableLiveData<String> = MutableLiveData("")

    //중복클릭시간차이
    private val MIN_CLICK_INTERVAL: Long = 2000

    //마지막으로 클릭한 시간
    private var mLastClickTime: Long = 0

    private val _isEmailEmpty: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _isPwEmpty: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _isPwConfirmEmpty: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _pwNotMatch: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _signUp: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _signUpSuccess: SingleLiveEvent<Unit> = SingleLiveEvent()

    val isEmailEmpty: LiveData<Unit> get() = _isEmailEmpty
    val isPwEmpty: LiveData<Unit> get() = _isPwEmpty
    val isPwConfirmEmpty: LiveData<Unit> get() = _isPwConfirmEmpty
    val pwNotMatch: LiveData<Unit> get() = _pwNotMatch
    val signUp: LiveData<Unit> get() = _signUp
    val signUpSuccess: LiveData<Unit> get() = _signUpSuccess

    fun onSignUpClick() {
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime: Long = SystemClock.uptimeMillis() - mLastClickTime
        mLastClickTime = currentClickTime
        if (elapsedTime <= MIN_CLICK_INTERVAL)
            return
        val email: String = email.value.toString().trim()
        val pw: String = pw.value.toString().trim()
        val pwConfirm: String = pwConfirm.value.toString().trim()
        when {
            email.isBlank() -> {
                _isEmailEmpty.call()
            }
            pw.isBlank() -> {
                _isPwEmpty.call()
            }
            pwConfirm.isBlank() -> {
                _isPwConfirmEmpty.call()
            }
            pw != pwConfirm -> {
                _pwNotMatch.call()

            }
            else -> _signUp.call()
        }
    }

    fun insertUser(user: User) {
        compositeDisposable.add(
            repository.insertUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showLottieProgress() }
                .doAfterTerminate { hideLottieProgress() }
                .subscribeBy(
                    onComplete = { _signUpSuccess.call() },
                    onError = { Log.d(TAG, "EmailSignUpViewModel insertUser() error -> $it") }
                )
        )
    }

}