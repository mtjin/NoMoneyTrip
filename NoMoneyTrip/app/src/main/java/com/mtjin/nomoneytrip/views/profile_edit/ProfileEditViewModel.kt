package com.mtjin.nomoneytrip.views.profile_edit

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.data.profile_edit.source.ProfileEditRepository
import com.mtjin.nomoneytrip.utils.ERR_DUPLICATE_NAME
import com.mtjin.nomoneytrip.utils.SingleLiveEvent
import com.mtjin.nomoneytrip.utils.TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ProfileEditViewModel(private val repository: ProfileEditRepository) : BaseViewModel() {
    lateinit var imageUri: Uri
    var name = MutableLiveData<String>("")


    private val _user = MutableLiveData<User>()
    private val _reviseImage = SingleLiveEvent<Unit>()
    private var _nameErrMsg = SingleLiveEvent<Int>()
    private var _editSuccessMsg = SingleLiveEvent<Boolean>()
    private var _nameDuplicateMsg = SingleLiveEvent<Unit>()

    val user: LiveData<User> get() = _user
    val reviseImage: LiveData<Unit> get() = _reviseImage
    val nameErrMsg: LiveData<Int> get() = _nameErrMsg
    val editSuccessMsg: LiveData<Boolean> get() = _editSuccessMsg
    val nameDuplicateMsg: LiveData<Unit> get() = _nameDuplicateMsg

    fun setUser(user: User) {
        _user.value = user
    }

    fun updateProfile() {
        when {
            name.value.isNullOrBlank() -> _nameErrMsg.call()
            !this::imageUri.isInitialized -> {
                compositeDisposable.add(
                    repository.updateName(name = name.value.toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { showLottieProgress() }
                        .doAfterTerminate { hideLottieProgress() }
                        .subscribeBy(
                            onError = {
                                if (it.localizedMessage == ERR_DUPLICATE_NAME) _nameDuplicateMsg.call()
                                else _editSuccessMsg.value = false
                                Log.d(TAG, "ProfileEditViewModel ")
                            },
                            onComplete = {
                                _editSuccessMsg.value = true
                            }
                        ))
            }
            else -> {
                compositeDisposable.add(
                    repository.updateProfile(name = name.value.toString(), imageUri = imageUri)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { showLottieProgress() }
                        .doAfterTerminate { hideLottieProgress() }
                        .subscribeBy(
                            onError = {
                                if (it.localizedMessage == ERR_DUPLICATE_NAME) _nameDuplicateMsg.call()
                                else _editSuccessMsg.value = false
                                Log.d(TAG, "ProfileEditViewModel ")
                            },
                            onComplete = {
                                _editSuccessMsg.value = true
                            }
                        ))
            }
        }
    }

    fun reviseImage() {
        _reviseImage.call()
    }
}