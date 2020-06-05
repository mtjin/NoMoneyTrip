package com.mtjin.nomoneytrip.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    protected fun showProgress() {
        _isLoading.value = true
    }

    protected fun hideProgress() {
        _isLoading.value = false
    }
}