package com.mtjin.nomoneytrip.views.email_signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class EmailSignUpViewModel : BaseViewModel() {
    val email: MutableLiveData<String> = MutableLiveData("")
    val pw: MutableLiveData<String> = MutableLiveData("")
    val pwConfirm: MutableLiveData<String> = MutableLiveData("")

    private val _isEmailEmpty: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _isPwEmpty: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _isPwConfirmEmpty: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _pwNotMatch: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _signUp: SingleLiveEvent<Unit> = SingleLiveEvent()

    val isEmailEmpty: LiveData<Unit> get() = _isEmailEmpty
    val isPwEmpty: LiveData<Unit> get() = _isPwEmpty
    val isPwConfirmEmpty: LiveData<Unit> get() = _isPwConfirmEmpty
    val pwNotMatch: LiveData<Unit> get() = _pwNotMatch
    val signUp: LiveData<Unit> get() = _signUp

    fun onSignUpClick() {
        showProgress()
        val email: String = email.value.toString().trim()
        val pw: String = pw.value.toString().trim()
        val pwConfirm: String = pwConfirm.value.toString().trim()

        if (email.isEmpty()) {
            _isEmailEmpty.call()
        } else if (pw.isEmpty()) {
            _isPwEmpty.call()
        } else if (pwConfirm.isEmpty()) {
            _isPwConfirmEmpty.call()
        } else if (pw != pwConfirm) {
            _pwNotMatch.call()
        } else {
            _signUp.call()
        }
    }

}