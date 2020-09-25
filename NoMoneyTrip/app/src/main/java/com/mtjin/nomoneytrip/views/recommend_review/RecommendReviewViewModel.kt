package com.mtjin.nomoneytrip.views.recommend_review

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.recommend_review.source.RecommendReviewRepository
import com.mtjin.nomoneytrip.utils.TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class RecommendReviewViewModel(private val repository: RecommendReviewRepository) :
    BaseViewModel() {
    private val _userReviewList = MutableLiveData<List<UserReview>>()

    val userReviewList: LiveData<List<UserReview>> get() = _userReviewList

    fun requestMyRecommendReviews() {
        compositeDisposable.add(
            repository.requestMyRecommendReviews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        _userReviewList.value = it
                    },
                    onError = {
                        Log.d(
                            TAG,
                            "RecommendReviewViewModel requestMyRecommendReviews() -> $it"
                        )
                    }
                )
        )
    }

    fun updateReviewRecommend(userReview: UserReview) {
        compositeDisposable.add(
            repository.updateReviewRecommend(userReview)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doAfterTerminate { hideProgress() }
                .subscribeBy(
                    onComplete = {},
                    onError = { Log.d(TAG, it.toString()) }
                )
        )
    }
}