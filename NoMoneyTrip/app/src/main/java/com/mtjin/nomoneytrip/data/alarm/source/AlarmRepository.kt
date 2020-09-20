package com.mtjin.nomoneytrip.data.alarm.source

import com.mtjin.nomoneytrip.data.alarm.Alarm
import io.reactivex.Single

interface AlarmRepository {
    fun requestAlarms(): Single<List<Alarm>>
}