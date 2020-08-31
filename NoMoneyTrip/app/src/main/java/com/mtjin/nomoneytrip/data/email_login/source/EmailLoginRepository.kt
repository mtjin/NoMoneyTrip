package com.mtjin.nomoneytrip.data.email_login.source

import com.mtjin.nomoneytrip.data.login.User
import io.reactivex.Completable

interface EmailLoginRepository{
    fun insertUser(user: User) : Completable
}