package com.mtjin.nomoneytrip.views.profile

import android.util.Log
import androidx.lifecycle.LiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
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

    val user: LiveData<User> get() = _user
    val goProfileEdit: LiveData<Unit> get() = _goProfileEdit

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

    fun goProfileEdit() {
        _goProfileEdit.call()
    }
}