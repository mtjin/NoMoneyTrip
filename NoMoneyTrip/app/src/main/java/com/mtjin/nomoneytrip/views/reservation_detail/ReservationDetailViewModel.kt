package com.mtjin.nomoneytrip.views.reservation_detail

import android.util.Log
import androidx.lifecycle.LiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.reservation_history.ReservationProduct
import com.mtjin.nomoneytrip.data.reservation_history.source.ReservationHistoryRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent
import com.mtjin.nomoneytrip.utils.TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ReservationDetailViewModel(private val repository: ReservationHistoryRepository) :
    BaseViewModel() {
    private val _updateReservationCancelSuccessMsg = SingleLiveEvent<Boolean>()
    private val _onClickCancelReservation = SingleLiveEvent<Unit>()

    val updateReservationCancelSuccessMsg: LiveData<Boolean> get() = _updateReservationCancelSuccessMsg
    val onClickCancelReservation: LiveData<Unit> get() = _onClickCancelReservation

    fun onClickCancelReservation() {
        _onClickCancelReservation.call()
    }

    

    fun updateReservationCancel(reservationProduct: ReservationProduct) {
        compositeDisposable.add(
            repository.updateReservationCancel(reservationProduct = reservationProduct)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showLottieProgress() }
                .doAfterTerminate { hideLottieProgress() }
                .subscribeBy(
                    onError = {
                        _updateReservationCancelSuccessMsg.value = false
                        Log.d(TAG, "updateReservationCancel() error")
                    },
                    onComplete = {
                        _updateReservationCancelSuccessMsg.value = true
                    }
                )
        )
    }
}