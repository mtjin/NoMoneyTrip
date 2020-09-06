package com.mtjin.nomoneytrip.views.reservation_phase_first

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel

class ReservationPhaseFirstViewModel : BaseViewModel() {
    private val _showCalendar = MutableLiveData<Boolean>(false)

    val showCalendar: LiveData<Boolean> get() = _showCalendar

    fun showCalendar() {
        _showCalendar.value = !(_showCalendar.value)!!
    }

}