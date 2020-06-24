package com.mtjin.nomoneytrip.views.email_signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class EmailSignUpViewModel : BaseViewModel() {
    val email: MutableLiveData<String> = MutableLiveData("")
    val pw: MutableLiveData<String> = MutableLiveData("")
    val pwConfirm: MutableLiveData<String> = MutableLiveData("")

    private val _isEmailEmpty: MutableLiveData<Unit> = SingleLiveEvent()
    private val _isPwEmpty: MutableLiveData<Unit> = SingleLiveEvent()
    private val _isPwConfirmEmpty: MutableLiveData<Unit> = SingleLiveEvent()
    private val _pwNotMatch: MutableLiveData<Unit> = SingleLiveEvent()
    private val _signUp: MutableLiveData<Unit> = SingleLiveEvent()

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
            _isEmailEmpty.value = Unit
        } else if (pw.isEmpty()) {
            _isPwEmpty.value = Unit
        } else if (pwConfirm.isEmpty()) {
            _isPwConfirmEmpty.value = Unit
        } else if (pw != pwConfirm) {
            _pwNotMatch.value = Unit
        } else {
            _signUp.value = Unit
        }
    }

}