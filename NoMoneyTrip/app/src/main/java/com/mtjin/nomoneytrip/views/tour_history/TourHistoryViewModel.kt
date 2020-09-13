package com.mtjin.nomoneytrip.views.tour_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.reservation_history.ReservationProduct
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class TourHistoryViewModel : BaseViewModel() {
    lateinit var selectedReservationProduct: ReservationProduct

    private val _resProList = MutableLiveData<List<ReservationProduct>>()
    private val _onNext = MutableLiveData<Boolean>(false)
    private val _goTourWrite = SingleLiveEvent<ReservationProduct>()

    val resProList: LiveData<List<ReservationProduct>> get() = _resProList
    val onNext: LiveData<Boolean> get() = _onNext
    val goTourWrite: LiveData<ReservationProduct> get() = _goTourWrite

    fun setReservationList(items: List<ReservationProduct>) {
        _resProList.value = items
    }

    fun setOnNext(isSelected: Boolean) {
        _onNext.value = isSelected
    }

    fun goTourWrite() {
        if (onNext.value!!) {
            _goTourWrite.value = selectedReservationProduct
        }
    }
}