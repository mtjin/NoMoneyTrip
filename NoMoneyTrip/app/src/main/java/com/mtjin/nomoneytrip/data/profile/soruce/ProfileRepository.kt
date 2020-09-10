package com.mtjin.nomoneytrip.data.profile.soruce

import com.mtjin.nomoneytrip.data.login.User
import io.reactivex.Single

interface ProfileRepository {
    fun requestProfile(): Single<User>
}