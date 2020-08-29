package com.mtjin.nomoneytrip.views.search

import android.util.Log
import androidx.lifecycle.LiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.utils.SingleLiveEvent
import com.mtjin.nomoneytrip.utils.TAG

class SearchViewModel : BaseViewModel() {

    private val _goLocal = SingleLiveEvent<String>()

    val goLocal: LiveData<String> get() = _goLocal

    fun goLocation(local: String) {
        Log.d(TAG, "SearchViewModel goLocation -> $local")
        _goLocal.value = local
    }
}