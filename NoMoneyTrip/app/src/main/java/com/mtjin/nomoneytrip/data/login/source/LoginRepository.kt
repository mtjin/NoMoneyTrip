package com.mtjin.nomoneytrip.data.login.source

import com.kakao.auth.Session

interface LoginRepository {
    fun kakaoLogin() : Session
}