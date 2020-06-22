package com.mtjin.nomoneytrip.data.login.source

import com.kakao.auth.Session
import com.mtjin.nomoneytrip.data.login.source.remote.LoginRemoteDataSource

class LoginRepositoryImpl(private val loginRemoteDataSource: LoginRemoteDataSource) :
    LoginRepository {
    override fun kakaoLogin(): Session = loginRemoteDataSource.kakaoLogin()
}