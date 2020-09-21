package com.mtjin.nomoneytrip.views.setting

import androidx.lifecycle.LiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.setting.source.SettingRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class SettingViewModel(private val repository: SettingRepository) : BaseViewModel() {
    private val _goLogout = SingleLiveEvent<Unit>()

    val goLogout: LiveData<Unit> = _goLogout
    fun setSettingAlarm(on: Boolean) {
        repository.alarmSetting = on
    }

    fun getSettingAlarm(): Boolean = repository.alarmSetting

    fun onInfoClick() {

    }

    fun onRuleClick() {

    }

    fun onPersonalInfoClick() {

    }

    fun onLocationInfoClick() {

    }

    fun onLogoutClick() {
        _goLogout.call()
    }

    fun onDeleteAuthClick() {

    }
}