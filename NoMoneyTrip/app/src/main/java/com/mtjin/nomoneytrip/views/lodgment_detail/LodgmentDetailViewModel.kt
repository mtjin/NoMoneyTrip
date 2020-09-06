package com.mtjin.nomoneytrip.views.lodgment_detail

import androidx.lifecycle.LiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class LodgmentDetailViewModel : BaseViewModel() {
    private val _goReservationFirst = SingleLiveEvent<Unit>()

    val goReservationFirst: LiveData<Unit> get() = _goReservationFirst

    fun goReservationFirst() {
        _goReservationFirst.call()
    }

}