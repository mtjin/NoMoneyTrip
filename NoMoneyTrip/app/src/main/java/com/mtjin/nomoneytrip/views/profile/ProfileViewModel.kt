package com.mtjin.nomoneytrip.views.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.home.Product
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
    private val _goSetting = SingleLiveEvent<Unit>()
    private val _userReviewList = MutableLiveData<List<UserReview>>()
    private val _productList = MutableLiveData<List<Product>>()
    private val _clickMyTour = SingleLiveEvent<Unit>()
    private val _clickHeart = SingleLiveEvent<Unit>()
    private val _clickFavorite = SingleLiveEvent<Unit>()

    val user: LiveData<User> get() = _user
    val goProfileEdit: LiveData<Unit> get() = _goProfileEdit
    val goSetting: LiveData<Unit> get() = _goSetting
    val userReviewList: LiveData<List<UserReview>> get() = _userReviewList
    val productList: LiveData<List<Product>> get() = _productList
    val clickMyTour: LiveData<Unit> get() = _clickMyTour
    val clickHeart: LiveData<Unit> get() = _clickHeart
    val clickFavorite: LiveData<Unit> get() = _clickFavorite

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

    fun requestMyReviews() {
        _clickMyTour.call()
        compositeDisposable.add(
            profileRepository.requestMyReviews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        _userReviewList.value = it
                    },
                    onError = {
                        Log.d(TAG, "ProfileViewModel requestMyReviews() -> $it")
                    }
                )
        )
    }

    fun requestMyRecommendReviews() {
        _clickHeart.call()
        compositeDisposable.add(
            profileRepository.requestMyRecommendReviews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        _userReviewList.value = it
                    },
                    onError = {
                        Log.d(TAG, "ProfileViewModel requestMyRecommendReviews() -> $it")
                    }
                )
        )
    }

    fun requestMyFavorites() {
        _clickFavorite.call()
        compositeDisposable.add(
            profileRepository.requestFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        _productList.value = it
                    },
                    onError = {
                        Log.d(TAG, "ProfileViewModel requestMyFavorites() -> $it")
                    }
                )
        )
    }

    fun updateReviewRecommend(userReview: UserReview) {
        _clickHeart.call()
        compositeDisposable.add(
            profileRepository.updateReviewRecommend(userReview)
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

    fun updateProductFavorite(product: Product) {
        compositeDisposable.add(
            profileRepository.updateProductFavorite(product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onError = {
                        Log.d(TAG, it.toString())
                    },
                    onComplete = {

                    }
                ))
    }

    fun goProfileEdit() {
        _goProfileEdit.call()
    }

    fun goSetting() {
        _goSetting.call()
    }
}