package com.mtjin.nomoneytrip.views.community

import android.util.Log
import androidx.lifecycle.LiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.community.source.CommunityRepository
import com.mtjin.nomoneytrip.data.reservation.Reservation
import com.mtjin.nomoneytrip.utils.SingleLiveEvent
import com.mtjin.nomoneytrip.utils.TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class CommunityViewModel(private val repository: CommunityRepository) : BaseViewModel() {

    private val _goTourHistory = SingleLiveEvent<List<Reservation>>()
    private val _goTourNoHistory = SingleLiveEvent<Unit>()

    val goTourHistory: LiveData<List<Reservation>> get() = _goTourHistory
    val goTourNoHistory: LiveData<Unit> get() = _goTourNoHistory

    fun goReview() {
        compositeDisposable.add(
            repository.requestMyReservations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        if (it.isEmpty()) _goTourNoHistory.call()
                        else _goTourHistory.value = it

                    },
                    onError = {
                        Log.d(TAG, "CommunityViewModel goReview() -> $it")
                    }
                )
        )
    }
}