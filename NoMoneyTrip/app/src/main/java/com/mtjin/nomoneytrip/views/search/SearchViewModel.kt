package com.mtjin.nomoneytrip.views.search

import androidx.lifecycle.LiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class SearchViewModel : BaseViewModel() {

    private val _goLocal = SingleLiveEvent<String>()

    val goLocal: LiveData<String> get() = _goLocal

    fun goLocation(local: String) {
        _goLocal.value = local
    }
}