package com.mtjin.nomoneytrip.views.profile_edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.login.User

class ProfileEditViewModel : BaseViewModel() {
    private val _user = MutableLiveData<User>()
    var name = MutableLiveData<String>("")

    val user: LiveData<User> get() = _user

    fun setUser(user: User) {
        _user.value = user
    }

    fun requestReviseProfile() {

    }
}