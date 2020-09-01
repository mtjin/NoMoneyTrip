package com.mtjin.nomoneytrip.data.login.source.remote

import com.kakao.auth.Session
import com.mtjin.nomoneytrip.data.login.User
import io.reactivex.Completable

interface LoginRemoteDataSource {
    fun kakaoLogin(): Session
    fun insertUser(user: User): Completable
    fun updateFCM(): Completable
}