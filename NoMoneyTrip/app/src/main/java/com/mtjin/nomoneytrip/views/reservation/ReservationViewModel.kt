package com.mtjin.nomoneytrip.views.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class ReservationViewModel : BaseViewModel() {
    var consentCheck = MutableLiveData<Boolean>(false)

    private val _consentMsg = SingleLiveEvent<Unit>()

    val consentMsg: LiveData<Unit> get() = _consentMsg

    fun requestReservation() {
        if (consentCheck.value == false) {
            _consentMsg.call()
        } else {

        }
    }
}