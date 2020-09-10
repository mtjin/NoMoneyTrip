package com.mtjin.nomoneytrip.views.tour_no_history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.tour_no_history.source.TourNoHistoryRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent
import com.mtjin.nomoneytrip.utils.TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class TourNoHistoryViewModel(private val repository: TourNoHistoryRepository) : BaseViewModel() {
    private val _productList = MutableLiveData<List<Product>>()
    private val _goHome = SingleLiveEvent<Unit>()

    val productList: LiveData<List<Product>> get() = _productList
    val goHome: LiveData<Unit> get() = _goHome

    fun requestProducts() {
        compositeDisposable.add(
            repository.requestProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        _productList.value = it
                    },
                    onError = {
                        Log.d(TAG, "TourNoHistoryViewModel requestProducts() -> $it")
                    }
                )
        )
    }

    fun goHome() {
        _goHome.call()
    }
}