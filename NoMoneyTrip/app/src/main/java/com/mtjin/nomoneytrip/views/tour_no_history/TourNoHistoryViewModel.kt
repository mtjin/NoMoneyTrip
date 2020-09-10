package com.mtjin.nomoneytrip.views.tour_no_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.home.Product

class TourNoHistoryViewModel : BaseViewModel() {
    private val _productList = MutableLiveData<Product>()

    val productList : LiveData<Product> get() = _productList

    fun requestProducts(){

    }
}