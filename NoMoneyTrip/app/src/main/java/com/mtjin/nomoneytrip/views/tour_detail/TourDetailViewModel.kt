package com.mtjin.nomoneytrip.views.tour_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.local_page.TourIntroduce
import com.mtjin.nomoneytrip.data.tour_detail.Item
import com.mtjin.nomoneytrip.data.tour_detail.source.TourDetailRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent
import com.mtjin.nomoneytrip.utils.TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class TourDetailViewModel(private val repository: TourDetailRepository) : BaseViewModel() {
    private val _tourProductDetail = MutableLiveData<Item>()
    private val _requestSuccessMsg = MutableLiveData<Boolean>()
    private val _searchDirection = SingleLiveEvent<Unit>()
    private val _share = SingleLiveEvent<Unit>()

    val tourProductDetail: LiveData<Item> get() = _tourProductDetail
    val requestSuccessMsg: LiveData<Boolean> get() = _requestSuccessMsg
    val searchDirection: LiveData<Unit> get() = _searchDirection
    val share: LiveData<Unit> get() = _share

    fun requestTourProductDetails(tourIntroduce: TourIntroduce) {
        compositeDisposable.add(
            repository.requestTourProductDetails(tourIntroduce)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        if (it.response.header.resultMsg == "OK") {
                            _tourProductDetail.value = it.response.body.items.item
                            _requestSuccessMsg.value = true
                        } else {
                            _requestSuccessMsg.value = false
                        }
                    },
                    onError = {
                        Log.d(TAG, it.toString())
                    }
                )
        )
    }

    fun searchDirection() {
        _searchDirection.call()
    }

    fun onClickShare() {
        _share.call()
    }
}