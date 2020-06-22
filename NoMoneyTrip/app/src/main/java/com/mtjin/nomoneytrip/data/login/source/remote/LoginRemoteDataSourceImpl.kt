package com.mtjin.nomoneytrip.data.login.source.remote

import com.kakao.auth.Session

class LoginRemoteDataSourceImpl : LoginRemoteDataSource {
    override fun kakaoLogin(): Session = Session.getCurrentSession()
}