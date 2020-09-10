package com.mtjin.nomoneytrip.views.tour_no_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.tour_no_history.source.TourNoHistoryRepository

class TourNoHistoryViewModel(private val repository: TourNoHistoryRepository) : BaseViewModel() {
    private val _productList = MutableLiveData<Product>()

    val productList: LiveData<Product> get() = _productList

    fun requestProducts() {

    }
}