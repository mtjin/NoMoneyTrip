package com.mtjin.nomoneytrip.data.setting.source

import com.mtjin.nomoneytrip.utils.PreferenceManager

class SettingRepositoryImpl(private val preferenceManager: PreferenceManager) : SettingRepository {
    override var alarmSetting: Boolean
        get() = preferenceManager.alarmSetting
        set(value) {
            preferenceManager.alarmSetting = value
        }
}