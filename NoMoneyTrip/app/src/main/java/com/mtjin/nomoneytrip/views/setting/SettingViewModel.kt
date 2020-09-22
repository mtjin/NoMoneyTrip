package com.mtjin.nomoneytrip.views.setting

import androidx.lifecycle.LiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.setting.source.SettingRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class SettingViewModel(private val repository: SettingRepository) : BaseViewModel() {
    private val _goLogout = SingleLiveEvent<Unit>()
    private val _onInfoClick = SingleLiveEvent<Unit>()
    private val _onRuleClick = SingleLiveEvent<Unit>()
    private val _onPersonalInfoClick = SingleLiveEvent<Unit>()
    private val _onLocationInfoClick = SingleLiveEvent<Unit>()
    private val _onDeleteAuthClick = SingleLiveEvent<Unit>()

    val goLogout: LiveData<Unit> = _goLogout
    val onInfoClick: LiveData<Unit> = _onInfoClick
    val onRuleClick: LiveData<Unit> = _onRuleClick
    val onPersonalInfoClick: LiveData<Unit> = _onPersonalInfoClick
    val onLocationInfoClick: LiveData<Unit> = _onLocationInfoClick
    val onDeleteAuthClick: LiveData<Unit> = _onDeleteAuthClick

    fun setSettingAlarm(on: Boolean) {
        repository.alarmSetting = on
    }

    fun getSettingAlarm(): Boolean = repository.alarmSetting

    fun onInfoClick() {
        _onInfoClick.call()
    }

    fun onRuleClick() {
        _onRuleClick.call()
    }

    fun onPersonalInfoClick() {
        _onPersonalInfoClick.call()
    }

    fun onLocationInfoClick() {
        _onLocationInfoClick.call()
    }

    fun onLogoutClick() {
        _goLogout.call()
    }

    fun onDeleteAuthClick() {
        _onDeleteAuthClick.call()
    }
}