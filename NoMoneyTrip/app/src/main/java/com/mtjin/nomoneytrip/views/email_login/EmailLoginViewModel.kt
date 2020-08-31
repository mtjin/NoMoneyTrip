package com.mtjin.nomoneytrip.views.email_login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.email_login.source.EmailLoginRepository
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class EmailLoginViewModel(private val emailLoginRepository: EmailLoginRepository) :
    BaseViewModel() {
    val email: MutableLiveData<String> = MutableLiveData("")
    val pw: MutableLiveData<String> = MutableLiveData("")
    private val _isEmailEmpty: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _isPwEmpty: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _login: SingleLiveEvent<Unit> = SingleLiveEvent()

    val isEmailEmpty: LiveData<Unit> get() = _isEmailEmpty
    val isPwEmpty: LiveData<Unit> get() = _isPwEmpty
    val login: LiveData<Unit> get() = _login

    fun onEmailLoginClick() {
        showProgress()
        val email: String = email.value.toString().trim()
        val pw: String = pw.value.toString().trim()
        if (email.isEmpty()) {
            _isEmailEmpty.call()
        } else if (pw.isEmpty()) {
            _isPwEmpty.call()
        } else {
            _login.call()
        }
    }

    fun insertUser(user: User) {
        emailLoginRepository
    }
}