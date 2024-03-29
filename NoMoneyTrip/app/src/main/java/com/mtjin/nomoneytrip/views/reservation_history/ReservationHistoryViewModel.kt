package com.mtjin.nomoneytrip.views.reservation_history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.reservation_history.ReservationProduct
import com.mtjin.nomoneytrip.data.reservation_history.source.ReservationHistoryRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent
import com.mtjin.nomoneytrip.utils.TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ReservationHistoryViewModel(private val repository: ReservationHistoryRepository) :
    BaseViewModel() {
    private val _reservationList = MutableLiveData<ArrayList<ReservationProduct>>()
    private val _deleteReservationSuccessMsg = SingleLiveEvent<Boolean>()
    private val _updateReservationCancelSuccessMsg = SingleLiveEvent<Boolean>()
    private val _goFavorite = SingleLiveEvent<Unit>()

    val reservationList: LiveData<ArrayList<ReservationProduct>> get() = _reservationList
    val deleteReservationSuccessMsg: LiveData<Boolean> get() = _deleteReservationSuccessMsg
    val updateReservationCancelSuccessMsg: LiveData<Boolean> get() = _updateReservationCancelSuccessMsg
    val goFavorite: LiveData<Unit> get() = _goFavorite

    fun requestReservations() {
        compositeDisposable.add(
            repository.requestReservations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onError = { Log.d(TAG, "requestReservations() error") },
                    onNext = {
                        _reservationList.value = it as ArrayList<ReservationProduct>
                    }
                )
        )
    }

    fun goFavorite() {
        _goFavorite.call()
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