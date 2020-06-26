package com.mtjin.nomoneytrip.views.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel

class HomeViewModel : BaseViewModel() {
    private val _goSearch: MutableLiveData<Unit> = MutableLiveData()

    val goSearch: LiveData<Unit> get() = _goSearch

    fun goSearch() {
        _goSearch.value = Unit
    }
}