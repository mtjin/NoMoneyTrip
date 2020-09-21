package com.mtjin.nomoneytrip.views.reservation_phase_first

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.reservation.Reservation
import com.mtjin.nomoneytrip.data.reservation_phase_first.source.ReservationPhaseFirstRepository
import com.mtjin.nomoneytrip.utils.*
import com.prolificinteractive.materialcalendarview.CalendarDay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ReservationPhaseFirstViewModel(private val repository: ReservationPhaseFirstRepository) :
    BaseViewModel() {
    var isDateSelected = false
    var startDateTimestamp: Long = 0
    var endDateTimestamp: Long = 0
    var selectedOption: String = ""

    private val _showCalendar = MutableLiveData<Boolean>(false)
    private val _dateRange = MutableLiveData<String>("날짜를 선택해주세요")
    private val _dayRange = MutableLiveData<String>("")
    private val _dateList = MutableLiveData<ArrayList<Reservation>>()
    private val _goReservation = SingleLiveEvent<Unit>()
    private val _num = MutableLiveData<String>("1")
    private val _option1 = MutableLiveData<Boolean>(false)
    private val _option2 = MutableLiveData<Boolean>(false)
    private val _allSelected = MutableLiveData<Boolean>(false)
    private val isAllSelected: Boolean get() = (isDateSelected && (option1.value == true || option2.value == true))

    val showCalendar: LiveData<Boolean> get() = _showCalendar
    val dateRange: LiveData<String> get() = _dateRange
    val dayRange: LiveData<String> get() = _dayRange
    val dateList: LiveData<ArrayList<Reservation>> get() = _dateList
    val goReservation: LiveData<Unit> get() = _goReservation
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
        setAllSelected()
    }

    fun option2Click() {
        if (option2.value == true) {
            _option2.value = false
        } else {
            _option2.value = true
            _option1.value = false
        }
        setAllSelected()
    }

    fun setAllSelected() {
        _allSelected.value = isAllSelected
    }

    fun goReservation() {
        if (isAllSelected) _goReservation.call()
    }

    fun requestReservations(product: Product) {
        compositeDisposable.add(
            repository.requestReservations(product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        _dateList.value = it as ArrayList<Reservation>
                    },
                    onError = {
                        Log.d(
                            TAG,
                            "ReservationPhaseFirstViewModel requestReservations() Error -> $it"
                        )
                    }
                )
        )
    }

    fun checkDatesAvailable(dates: List<CalendarDay>): Boolean {
        for (date in dates) {
            for (reservedDate in dateList.value!!) {
                convertDateToTimestamp(
                    _year = date.year,
                    _month = date.month,
                    _day = date.day
                ).let {
                    if (it < reservedDate.endDateTimestamp && it >= reservedDate.startDateTimestamp) {
                        isDateSelected = false
                        setAllSelected()
                        return false
                    }
                }
            }
        }
        return true
    }

    fun setStartDateTimestamp(date: CalendarDay) {
        startDateTimestamp = convertDateToTimestamp(
            _year = date.year,
            _month = date.month,
            _day = date.day
        )
    }

    fun setEndDateTimestamp(date: CalendarDay) {
        endDateTimestamp = convertDateToTimestamp(
            _year = date.year,
            _month = date.month,
            _day = date.day
        )
    }

    fun setSingleDateRange() {
        _dateRange.value =
            startDateTimestamp.convertTimestampToPointFullDate()
        _dayRange.value = "0박"
    }

    fun setDateRange() {
        _dateRange.value =
            startDateTimestamp.convertTimestampToPointFullDate() + "~" + endDateTimestamp.convertTimestampToPointFullDate()
        _dayRange.value =
            (endDateTimestamp.convertTimestampToDay() - startDateTimestamp.convertTimestampToDay()).toString() + "박"
    }

    fun initDateRange() {
        _dateRange.value = "날짜를 선택해주세요"
        _dayRange.value = ""
    }
}