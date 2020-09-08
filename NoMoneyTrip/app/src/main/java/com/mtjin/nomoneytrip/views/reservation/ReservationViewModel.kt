package com.mtjin.nomoneytrip.views.reservation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.reservation.Reservation
import com.mtjin.nomoneytrip.data.reservation.source.ReservationRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ReservationViewModel(private val repository: ReservationRepository) : BaseViewModel() {
    var consentCheck = MutableLiveData<Boolean>(false)
    lateinit var reservation: Reservation

    private val _consentMsg = SingleLiveEvent<Unit>()
    private val _successReservation = SingleLiveEvent<Boolean>()

    val consentMsg: LiveData<Unit> get() = _consentMsg
    val successReservation: LiveData<Boolean> get() = _successReservation

    fun insertReservation() {
        Log.d("EEEEEE", "requestReservation()")
        if (consentCheck.value == false) {
            _consentMsg.call()
        } else {
            compositeDisposable.add(
                repository.insertReservation(reservation)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                        onComplete = { _successReservation.value = true },
                        onError = { _successReservation.value = false }
                    )
            )
        }
    }
}