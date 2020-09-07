package com.mtjin.nomoneytrip.views.reservation

import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel

class ReservationViewModel : BaseViewModel() {
    var consentCheck = MutableLiveData<Boolean>(false)
}