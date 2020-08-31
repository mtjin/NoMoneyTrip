package com.mtjin.nomoneytrip.views.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.home.source.HomeRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent
import com.mtjin.nomoneytrip.utils.TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val homeRepository: HomeRepository) : BaseViewModel() {
    private val _goSearch: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val _productList = MutableLiveData<ArrayList<Product>>()

    val goSearch: LiveData<Unit> get() = _goSearch
    val productList: LiveData<ArrayList<Product>> get() = _productList

    fun goSearch() {
        _goSearch.call()
    }

    fun requestProducts() {
        compositeDisposable.add(
            homeRepository.requestProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ products ->
                    _productList.value = products as ArrayList<Product>
                    hideProgress()
                }, {
                    Log.d(TAG, "HomeViewModel requestProducts() Error -> $it")
                })
        )
    }
}