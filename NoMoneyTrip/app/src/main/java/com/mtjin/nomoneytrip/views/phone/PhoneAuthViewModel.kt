package com.mtjin.nomoneytrip.views.phone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.phone.source.PhoneAuthRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class PhoneAuthViewModel(private val repository: PhoneAuthRepository) : BaseViewModel() {
    val phoneNum = MutableLiveData<String>("")
    val authNum = MutableLiveData<String>("")
    private val _requestAuth = SingleLiveEvent<Boolean>()

    val requestAuth: LiveData<Boolean> get() = _requestAuth

    fun requestAuth() {
        _requestAuth.value = !phoneNum.value.isNullOrBlank()
    }
}