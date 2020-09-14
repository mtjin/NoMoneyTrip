package com.mtjin.nomoneytrip.views.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.data.profile.soruce.ProfileRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent
import com.mtjin.nomoneytrip.utils.TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ProfileViewModel(private val profileRepository: ProfileRepository) : BaseViewModel() {

    private val _user = SingleLiveEvent<User>()
    private val _goProfileEdit = SingleLiveEvent<Unit>()
    private val _userReviewList = MutableLiveData<List<UserReview>>()

    val user: LiveData<User> get() = _user
    val goProfileEdit: LiveData<Unit> get() = _goProfileEdit
    val userReviewList: LiveData<List<UserReview>> get() = _userReviewList

    fun requestProfile() {
        compositeDisposable.add(
            profileRepository.requestProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Log.d(TAG, "ProfileViewModel requestProfile() error -> $it")
                        _user.value = it
                    },
                    onError = {
                        Log.d(TAG, "ProfileViewModel requestProfile() error -> $it")
                    }
                )
        )
    }

    fun requestReviews() {
        compositeDisposable.add(
            profileRepository.requestReviews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        _userReviewList.value = it
                    },
                    onError = {
                        Log.d(TAG, "ProfileViewModel requestReviews() -> $it")
                    }
                )
        )
    }

    fun updateReviewRecommend(userReview: UserReview) {
        compositeDisposable.add(
            profileRepository.updateReviewRecommend(userReview)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onComplete = {},
                    onError = { Log.d(TAG, it.toString()) }
                )
        )
    }

    fun goProfileEdit() {
        _goProfileEdit.call()
    }
}