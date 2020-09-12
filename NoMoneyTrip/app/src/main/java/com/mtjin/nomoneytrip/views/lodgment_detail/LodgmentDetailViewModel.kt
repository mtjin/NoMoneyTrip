package com.mtjin.nomoneytrip.views.lodgment_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.lodgment_detail.source.LodgmentDetailRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent
import com.mtjin.nomoneytrip.utils.TAG
import com.mtjin.nomoneytrip.utils.uuid
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class LodgmentDetailViewModel(private val repository: LodgmentDetailRepository) : BaseViewModel() {
    var page = 2 //리뷰 페이징
    lateinit var productId: String
    lateinit var product: Product

    private val _goReservationFirst = SingleLiveEvent<Unit>()
    private val _searchDirection = SingleLiveEvent<Unit>()
    private val _updateFavoriteResult = SingleLiveEvent<Boolean>()
    private val _userReviewList = MutableLiveData<List<UserReview>>()

    val goReservationFirst: LiveData<Unit> get() = _goReservationFirst
    val searchDirection: LiveData<Unit> get() = _searchDirection
    val updateFavoriteResult: LiveData<Boolean> get() = _updateFavoriteResult
    val userReviewList: LiveData<List<UserReview>> get() = _userReviewList

    fun goReservationFirst() {
        _goReservationFirst.call()
    }

    fun searchDirection() {
        _searchDirection.call()
    }

    fun requestReviews() {
        compositeDisposable.add(
            repository.requestReviews(productId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Log.d("AAAA", it.toString())
                        _userReviewList.value = it
                    },
                    onError = {
                        Log.d(TAG, "LodgmentDetailViewModel requestReviews() -> $it")
                    }
                )
        )
        page += 5
    }

    fun updateReviewRecommend(userReview: UserReview) {
        compositeDisposable.add(
            repository.updateReviewRecommend(userReview)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onComplete = {},
                    onError = { Log.d(TAG, it.toString()) }
                )
        )
    }

    fun updateProductFavorite() {
        if (product.favoriteList.contains(uuid)) product.favoriteList.remove(uuid)
        else product.favoriteList.add(uuid)
        compositeDisposable.add(
            repository.updateProductFavorite(product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onError = {
                        Log.d(TAG, it.toString())
                    },
                    onComplete = {
                        _updateFavoriteResult.value = product.favoriteList.contains(uuid)
                    }
                ))
    }
}