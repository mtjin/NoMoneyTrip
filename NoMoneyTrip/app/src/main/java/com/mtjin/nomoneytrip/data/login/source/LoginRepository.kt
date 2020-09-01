package com.mtjin.nomoneytrip.data.login.source

import com.kakao.auth.Session
import com.mtjin.nomoneytrip.data.login.User
import io.reactivex.Completable

interface LoginRepository {
    fun kakaoLogin() : Session
    fun insertUser(user : User) : Completable
    fun updateFCM(): Completable
}