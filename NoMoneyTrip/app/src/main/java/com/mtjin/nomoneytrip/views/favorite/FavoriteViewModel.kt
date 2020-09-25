package com.mtjin.nomoneytrip.views.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.favorite.source.FavoriteRepository
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.utils.TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class FavoriteViewModel(private val repository: FavoriteRepository) : BaseViewModel() {

    private val _productList = MutableLiveData<List<Product>>()

    val productList: LiveData<List<Product>> get() = _productList

    fun requestMyFavorites() {
        compositeDisposable.add(
            repository.requestFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        _productList.value = it
                    },
                    onError = {
                        Log.d(TAG, "FavoriteViewModel requestMyFavorites() -> $it")
                    }
                )
        )
    }
}