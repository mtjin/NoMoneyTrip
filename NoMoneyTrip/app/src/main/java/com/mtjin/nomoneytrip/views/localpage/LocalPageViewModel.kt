package com.mtjin.nomoneytrip.views.localpage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.community.Review
import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.local_page.TourIntroduce
import com.mtjin.nomoneytrip.data.local_page.source.LocalPageRepository
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.utils.SingleLiveEvent
import com.mtjin.nomoneytrip.utils.TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class LocalPageViewModel(private val repository: LocalPageRepository) : BaseViewModel() {
    var page = 2 //리뷰 페이징
    private var lastUserReview: UserReview = UserReview(User(), Review(), Product())
    lateinit var city: String
    private val _tourIntroduceList = MutableLiveData<ArrayList<TourIntroduce>>()
    private val _restaurantIntroduceList = MutableLiveData<ArrayList<TourIntroduce>>()
    private val _productList = MutableLiveData<ArrayList<Product>>()
    private val _userReviewList = MutableLiveData<List<UserReview>>()
    private val _goLodgeSearch = SingleLiveEvent<Unit>()
    private val _lastReviewCall = SingleLiveEvent<Unit>()

    val tourIntroduceList: LiveData<ArrayList<TourIntroduce>> get() = _tourIntroduceList
    val restaurantIntroduceList: LiveData<ArrayList<TourIntroduce>> get() = _restaurantIntroduceList
    val productList: LiveData<ArrayList<Product>> get() = _productList
    val userReviewList: LiveData<List<UserReview>> get() = _userReviewList
    val goLodgeSearch: LiveData<Unit> get() = _goLodgeSearch
    val lastReviewCall: LiveData<Unit> get() = _lastReviewCall

    fun requestTourIntroduces(areaCode: Int) {
        compositeDisposable.add(
            repository.requestTourIntroduces(areaCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ introduces ->
                    _tourIntroduceList.value = introduces as ArrayList<TourIntroduce>
                }, {
                    Log.d(TAG, "requestTourIntroduces() -> $it")
                })
        )
    }

    fun requestRestaurantIntroduces(areaCode: Int) {
        compositeDisposable.add(
            repository.requestRestaurantIntroduces(areaCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ introduces ->
                    _restaurantIntroduceList.value = introduces as ArrayList<TourIntroduce>
                }, {
                    Log.d(TAG, "requestRestaurantIntroduces() -> $it")
                })
        )
    }

    fun requestProducts() {
        compositeDisposable.add(
            repository.requestProducts(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ products ->
                    _productList.value = products as ArrayList<Product>
                }, {
                    Log.d(TAG, "requestProducts() -> $it")
                })
        )
    }

    fun requestReviews() {
        compositeDisposable.add(
            repository.requestReviews(city, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        when {
                            it.isNullOrEmpty() -> {
                                _userReviewList.value = it
                            }
                            lastUserReview.review.id == it[it.size - 1].review.id -> {
                                _lastReviewCall.call()
                            }
                            else -> {
                                lastUserReview = it[it.size - 1]
                                _userReviewList.value = it
                            }
                        }
                    },
                    onError = {
                        Log.d(TAG, "LocalPageViewModel requestReviews() -> $it")
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

    fun updateProductFavorite(product: Product) {
        compositeDisposable.add(
            repository.updateProductFavorite(product)
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

    fun goLodgeSearch() {
        _goLodgeSearch.call()
    }
}