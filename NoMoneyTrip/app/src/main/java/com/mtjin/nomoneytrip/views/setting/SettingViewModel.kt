package com.mtjin.nomoneytrip.views.setting

import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.setting.source.SettingRepository

class SettingViewModel(private val repository: SettingRepository) : BaseViewModel() {

    fun setSettingAlarm(on: Boolean) {
        repository.alarmSetting = on
    }

    fun getSettingAlarm(): Boolean = repository.alarmSetting
}