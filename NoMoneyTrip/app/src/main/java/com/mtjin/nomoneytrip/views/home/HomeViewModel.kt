package com.mtjin.nomoneytrip.views.home

import androidx.lifecycle.LiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class HomeViewModel : BaseViewModel() {
    private val _goSearch: SingleLiveEvent<Unit> = SingleLiveEvent()

    val goSearch: LiveData<Unit> get() = _goSearch

    fun goSearch() {
        _goSearch.call()
    }
}