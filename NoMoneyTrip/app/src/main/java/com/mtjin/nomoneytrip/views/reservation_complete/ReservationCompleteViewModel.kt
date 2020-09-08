package com.mtjin.nomoneytrip.views.reservation_complete

import androidx.lifecycle.LiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class ReservationCompleteViewModel : BaseViewModel() {
    lateinit var reservationId: String

    private val _goReservationDetail = SingleLiveEvent<Unit>()
    private val _goHome = SingleLiveEvent<Unit>()

    val goReservationDetail: LiveData<Unit> get() = _goReservationDetail
    val goHome: LiveData<Unit> get() = _goHome

    fun goReservationDetail() {
        _goReservationDetail.call()
    }

    fun goHome() {
        _goHome.call()
    }
}