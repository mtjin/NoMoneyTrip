package com.mtjin.nomoneytrip.data.login.source.remote

import com.kakao.auth.Session

interface LoginRemoteDataSource {
    fun kakaoLogin(): Session
}