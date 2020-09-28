package com.mtjin.nomoneytrip.views.tour_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.local_page.TourIntroduce
import com.mtjin.nomoneytrip.data.tour_detail.Item
import com.mtjin.nomoneytrip.data.tour_detail.source.TourDetailRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class TourDetailViewModel(private val repository: TourDetailRepository) : BaseViewModel() {
    private val _tourProductDetail = MutableLiveData<Item>()
    private val _requestSuccessMsg = MutableLiveData<Boolean>()
    private val _searchDirection = SingleLiveEvent<Unit>()

    val tourProductDetail: LiveData<Item> get() = _tourProductDetail
    val requestSuccessMsg: LiveData<Boolean> get() = _requestSuccessMsg
    val searchDirection: LiveData<Unit> get() = _searchDirection

    fun requestTourProductDetails(tourIntroduce: TourIntroduce) {
        compositeDisposable.add(
            repository.requestTourProductDetails(tourIntroduce)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Log.d("AAAAA", it.toString())
                        Log.d(
                            "AAAAAAAAA2312",
                            it.response.body.items.item.toString()
                        )
                        _tourProductDetail.value =
                            it.response.body.items.item
//                        if (it.response?.header?.resultMsg == "OK") {
//                            Log.d("AAAAAAAAA2312", it.response.body?.items?.item.toString())
//                            _tourProductDetail.value = it.response.body?.items?.item
//                            _requestSuccessMsg.value = true
//                        } else {
//                            _requestSuccessMsg.value = false
//                        }
                    },
                    onError = {
                        Log.d("AAAAAAAAA2312", "실패")
                        Log.d("AAAAAAAAA2312", it.toString())
                    }
                )
        )
    }

    fun searchDirection() {
        _searchDirection.call()
    }
}