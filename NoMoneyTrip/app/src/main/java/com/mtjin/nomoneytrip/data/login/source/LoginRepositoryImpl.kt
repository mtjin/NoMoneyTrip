package com.mtjin.nomoneytrip.data.login.source

import com.kakao.auth.Session
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.data.login.source.remote.LoginRemoteDataSource
import io.reactivex.Completable

class LoginRepositoryImpl(private val loginRemoteDataSource: LoginRemoteDataSource) :
    LoginRepository {
    override fun kakaoLogin(): Session = loginRemoteDataSource.kakaoLogin()
    override fun insertUser(user: User): Completable = loginRemoteDataSource.insertUser(user)
    override fun updateFCM(): Completable = loginRemoteDataSource.updateFCM()
}