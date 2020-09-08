package com.mtjin.nomoneytrip.views.reservation_history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.reservation.Reservation
import com.mtjin.nomoneytrip.data.reservation_history.ReservationHistory
import com.mtjin.nomoneytrip.data.reservation_history.source.ReservationHistoryRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent
import com.mtjin.nomoneytrip.utils.TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ReservationHistoryViewModel(private val repository: ReservationHistoryRepository) :
    BaseViewModel() {
    private val _reservationList = MutableLiveData<ArrayList<ReservationHistory>>()
    private val _deleteReservationSuccessMsg = SingleLiveEvent<Boolean>()

    val reservationList: LiveData<ArrayList<ReservationHistory>> get() = _reservationList
    val deleteReservationSuccessMsg: LiveData<Boolean> get() = _deleteReservationSuccessMsg

    fun requestReservations() {
        compositeDisposable.add(
            repository.requestReservations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onError = { Log.d(TAG, "requestReservations() error") },
                    onNext = {
                        _reservationList.value = it as ArrayList<ReservationHistory>
                    }
                )
        )
    }

    fun deleteReservation(reservation: Reservation) {
        compositeDisposable.add(
            repository.deleteReservation(reservation = reservation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onError = {
                        _deleteReservationSuccessMsg.value = false
                        Log.d(TAG, "requestReservations() error")
                    },
                    onComplete = {
                        _deleteReservationSuccessMsg.value = true
                    }
                )
        )
    }

}