package com.mtjin.nomoneytrip.views.alarm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.alarm.Alarm
import com.mtjin.nomoneytrip.data.alarm.source.AlarmRepository
import com.mtjin.nomoneytrip.utils.TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class AlarmViewModel(private val repository: AlarmRepository) : BaseViewModel() {

    private val _alarmList = MutableLiveData<List<Alarm>>()

    val alarmList: LiveData<List<Alarm>> = _alarmList

    fun requestNotifications() {
        compositeDisposable.add(
            repository.requestAlarms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        _alarmList.value = it
                        Log.d("AAAAA", it.toString())
                    },
                    onError = {
                        Log.d(
                            TAG,
                            "AlarmViewModel requestNotifications() -> $it"
                        )
                    }
                )
        )
    }

}