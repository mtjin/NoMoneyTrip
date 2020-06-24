package com.mtjin.nomoneytrip.views.email_login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class EmailLoginViewModel : BaseViewModel() {
    val email: MutableLiveData<String> = MutableLiveData("")
    val pw: MutableLiveData<String> = MutableLiveData("")
    private val _isEmailEmpty: MutableLiveData<Unit> = SingleLiveEvent()
    private val _isPwEmpty: MutableLiveData<Unit> = SingleLiveEvent()
    private val _login: MutableLiveData<Unit> = SingleLiveEvent()

    val isEmailEmpty: LiveData<Unit> get() = _isEmailEmpty
    val isPwEmpty: LiveData<Unit> get() = _isPwEmpty
    val login: LiveData<Unit> get() = _login

    fun onEmailLoginClick() {
        showProgress()
        val email: String = email.value.toString().trim()
        val pw: String = pw.value.toString().trim()
        if (email.isEmpty()) {
            _isEmailEmpty.value = Unit
        } else if (pw.isEmpty()) {
            _isPwEmpty.value = Unit
        } else {
            _login.value = Unit
        }
    }
}