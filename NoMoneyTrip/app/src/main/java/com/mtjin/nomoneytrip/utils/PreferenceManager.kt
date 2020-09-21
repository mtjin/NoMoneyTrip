package com.mtjin.nomoneytrip.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(RESERVATION_APP, Context.MODE_PRIVATE)

    var uuid: String
        get() = sharedPref.getString(UUID_KEY, "").toString()
        set(value) {
            val editor = sharedPref.edit()
            editor.putString(UUID_KEY, value)
            editor.commit()
        }

    var alarmSetting: Boolean
        get() = sharedPref.getBoolean(ALARM_SETTING_KEY, true)
        set(value) {
            val editor = sharedPref.edit()
            editor.putBoolean(ALARM_SETTING_KEY, value)
            editor.apply()
        }

    var masterIdInput: String
        get() = sharedPref.getString(MASTER_ID_KEY, "").toString()
        set(value) {
            val editor = sharedPref.edit()
            editor.putString(MASTER_ID_KEY, value)
            editor.apply()
        }

    var masterPwInput: String
        get() = sharedPref.getString(MASTER_PW_KEY, "").toString()
        set(value) {
            val editor = sharedPref.edit()
            editor.putString(MASTER_PW_KEY, value)
            editor.apply()
        }

    var masterProductId: String
        get() = sharedPref.getString(MASTER_PRODUCT_ID_KEY, "").toString()
        set(value) {
            val editor = sharedPref.edit()
            editor.putString(MASTER_PRODUCT_ID_KEY, value)
            editor.apply()
        }


    companion object {
        private const val RESERVATION_APP = "NO_MONEY_TRIP_APP"
        private const val UUID_KEY = "UUID_KEY"
        private const val ALARM_SETTING_KEY = "ALARM_SETTING_KEY"
        private const val MASTER_ID_KEY = "MASTER_ID_KEY"
        private const val MASTER_PW_KEY = "MASTER_PW_KEY"
        private const val MASTER_PRODUCT_ID_KEY = "MASTER_PRODUCT_ID_KEY"
    }
}