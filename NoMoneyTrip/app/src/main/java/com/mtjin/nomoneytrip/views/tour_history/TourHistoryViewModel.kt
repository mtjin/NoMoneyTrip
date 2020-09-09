package com.mtjin.nomoneytrip.views.tour_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.reservation_history.ReservationProduct

class TourHistoryViewModel : BaseViewModel() {
    private val _resProList = MutableLiveData<List<ReservationProduct>>()

    val resProList: LiveData<List<ReservationProduct>> get() = _resProList

    fun setReservationList(items: List<ReservationProduct>) {
        _resProList.value = items
    }
}