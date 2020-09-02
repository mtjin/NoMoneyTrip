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
    private val _hashTagList = MutableLiveData<ArrayList<String>>()

    val goSearch: LiveData<Unit> get() = _goSearch
    val productList: LiveData<ArrayList<Product>> get() = _productList
    val hashTagList: LiveData<ArrayList<String>> get() = _hashTagList

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
                    val hashTags = ArrayList<String>()
                    for (product in products) {
                        for (hashTag in product.hashTagList) {
                            hashTags.add(hashTag)
                        }
                    }
                    _hashTagList.value = hashTags.distinct() as ArrayList<String>
                }, {
                    Log.d(TAG, "HomeViewModel requestProducts() Error -> $it")
                })
        )
    }

    fun requestHashTagProducts(hashTag: String) {
        compositeDisposable.add(
            homeRepository.requestHashTagProducts(hashTag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ products ->
                    _productList.value = products as ArrayList<Product>
                }, {
                    Log.d(TAG, "HomeViewModel requestHashTagProducts() Error -> $it")
                })
        )
    }
}