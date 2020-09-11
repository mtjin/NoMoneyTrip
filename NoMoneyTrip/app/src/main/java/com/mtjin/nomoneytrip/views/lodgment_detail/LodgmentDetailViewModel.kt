package com.mtjin.nomoneytrip.views.lodgment_detail

import androidx.lifecycle.LiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class LodgmentDetailViewModel : BaseViewModel() {
    private val _goReservationFirst = SingleLiveEvent<Unit>()
    private val _searchDirection = SingleLiveEvent<Unit>()

    val goReservationFirst: LiveData<Unit> get() = _goReservationFirst
    val searchDirection: LiveData<Unit> get() = _searchDirection

    fun goReservationFirst() {
        _goReservationFirst.call()
    }

    fun searchDirection() {
        _searchDirection.call()
    }
}