package com.mtjin.nomoneytrip.views.reservation_phase_first

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class ReservationPhaseFirstViewModel : BaseViewModel() {
    var isDateSelected = false
    var startDateTimestamp: Long = 0
    var endDateTimestamp: Long = 0
    var selectedOption: String = ""

    private val _showCalendar = MutableLiveData<Boolean>(false)
    private val _goReservation = SingleLiveEvent<Unit>()
    private val _date = MutableLiveData<String>("")
    private val _num = MutableLiveData<String>("1")
    private val _option1 = MutableLiveData<Boolean>(false)
    private val _option2 = MutableLiveData<Boolean>(false)
    private val _allSelected = MutableLiveData<Boolean>(false)
    private val isAllSelected: Boolean get() = (isDateSelected && (option1.value == true || option2.value == true))

    val showCalendar: LiveData<Boolean> get() = _showCalendar
    val goReservation: LiveData<Unit> get() = _goReservation
    val date: LiveData<String> get() = _date
    val num: LiveData<String> get() = _num
    val option1: LiveData<Boolean> get() = _option1
    val option2: LiveData<Boolean> get() = _option2
    val allSelected: LiveData<Boolean> get() = _allSelected

    fun showCalendar() {
        _showCalendar.value = !(_showCalendar.value)!!
    }

    fun numUp() {
        _num.value = (_num.value!!.toInt() + 1).toString()
    }

    fun numDown() {
        if (_num.value!!.toInt() > 1) {
            _num.value = (_num.value!!.toInt() - 1).toString()
        }
    }

    fun option1Click() {
        if (option1.value == true) {
            _option1.value = false
        } else {
            _option1.value = true
            _option2.value = false
        }
        checkAllSelected()
    }

    fun option2Click() {
        if (option2.value == true) {
            _option2.value = false
        } else {
            _option2.value = true
            _option1.value = false
        }
        checkAllSelected()
    }

    fun checkAllSelected() {
        _allSelected.value = isAllSelected
    }

    fun goReservation() {
        if (isAllSelected) _goReservation.call()
    }

}