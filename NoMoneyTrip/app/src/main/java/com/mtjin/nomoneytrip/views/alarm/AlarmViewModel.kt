package com.mtjin.nomoneytrip.views.alarm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.alarm.Alarm
import com.mtjin.nomoneytrip.data.alarm.source.AlarmRepository
import com.mtjin.nomoneytrip.data.reservation_history.ReservationProduct
import com.mtjin.nomoneytrip.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class AlarmViewModel(private val repository: AlarmRepository) : BaseViewModel() {

    private val _alarmList = MutableLiveData<List<Alarm>>()
    private val _requestNotificationsResult = SingleLiveEvent<Unit>()
    private val _goReservationDetail = SingleLiveEvent<ReservationProduct>()
    private val _goTourHistory = SingleLiveEvent<List<ReservationProduct>>()
    private val _goTourNoHistory = SingleLiveEvent<Unit>()

    val alarmList: LiveData<List<Alarm>> = _alarmList
    val requestNotificationsResult: LiveData<Unit> = _requestNotificationsResult
    val goReservationDetail: LiveData<ReservationProduct> = _goReservationDetail
    val goTourHistory: LiveData<List<ReservationProduct>> get() = _goTourHistory
    val goTourNoHistory: LiveData<Unit> get() = _goTourNoHistory

    fun requestNotifications() {
        compositeDisposable.add(
            repository.requestAlarms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        _alarmList.value = it
                        _requestNotificationsResult.call()
                    },
                    onError = {
                        Log.d(
                            TAG,
                            "AlarmViewModel requestNotifications() Error -> $it"
                        )
                        _requestNotificationsResult.call()
                    }
                )
        )
    }

    fun updateAlarmReadState(alarm: Alarm) {
        compositeDisposable.add(
            repository.updateAlarmReadState(alarm = alarm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onComplete = {
                        if (alarm.case == ALARM_RESERVATION_COMPLETE_CASE1 || alarm.case == ALARM_RESERVATION_ACCEPT_CASE2 || alarm.case == ALARM_START_CASE3 || alarm.case == ALARM_RESERVATION_REQUEST_CASE5
                            || alarm.case == ALARM_RESERVATION_DENY_CASE6
                        ) {
                            goReservationDetail(alarm)
                        } else {
                            goReview()
                        }
                    },
                    onError = {
                        Log.d(
                            TAG,
                            "AlarmViewModel updateAlarmReadState() Error -> $it"
                        )
                    }
                )
        )
    }

    fun goReservationDetail(alarm: Alarm) {
        compositeDisposable.add(
            repository.requestReservation(alarm = alarm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        _goReservationDetail.value = it
                    },
                    onError = {
                        Log.d(
                            TAG,
                            "AlarmViewModel goReservationDetail() Error -> $it"
                        )
                    }
                )
        )
    }

    fun goReview() {
        compositeDisposable.add(
            repository.requestMyReservations()
                .map { it.filter { item -> !item.reservation.reviewed && item.reservation.endDateTimestamp <= getTimestamp() && item.reservation.state != 1 && item.reservation.state != 3 } }
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