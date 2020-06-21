package com.mtjin.nomoneytrip.views.login

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import android.os.Bundle
import android.util.Log
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseActivity
import com.mtjin.nomoneytrip.databinding.ActivityLoginBinding
import com.mtjin.nomoneytrip.views.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Session.getCurrentSession().addCallback(sessionCallback)
        iv_kakao_login.setOnClickListener {
            val session = Session.getCurrentSession()
            session.addCallback(SessionCallback())
            session.open(AuthType.KAKAO_LOGIN_ALL, this)
        }
    }

    // 세션 콜백 구현
    private val sessionCallback: ISessionCallback = object : ISessionCallback {
        override fun onSessionOpened() {
            Log.i(TAG, "로그인 성공")
            val intent: Intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.addFlags(FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }

        override fun onSessionOpenFailed(exception: KakaoException) {
            Log.e(TAG, "로그인 실패", exception)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    class SessionCallback : ISessionCallback {
        // 로그인에 성공한 상태
        override fun onSessionOpened() {
            requestMe()
        }

        // 로그인에 실패한 상태
        override fun onSessionOpenFailed(exception: KakaoException) {
            Log.e(TAG, "onSessionOpenFailed : " + exception.message)
        }

        // 사용자 정보 요청
        private fun requestMe() {
            UserManagement.getInstance()
                .me(object : MeV2ResponseCallback() {
                    override fun onSessionClosed(errorResult: ErrorResult) {
                        Log.e(TAG, "세션이 닫혀 있음: $errorResult")
                    }

                    override fun onFailure(errorResult: ErrorResult) {
                        Log.e(TAG, "사용자 정보 요청 실패: $errorResult")
                    }

                    override fun onSuccess(result: MeV2Response) {
                        Log.i(TAG, "사용자 아이디: " + result.id)
//                        val kakaoAccount: UserAccount = result.kakaoAccount
//
//                        // 이메일
//                        val email: String = kakaoAccount.email
//                        Log.i("KAKAO_API", "email: $email")
//
//                        // 프로필
//                        val profile: Profile = kakaoAccount.profile
//                        Log.d("KAKAO_API", "nickname: " + profile.nickname)
//                        Log.d(
//                            "KAKAO_API",
//                            "profile image: " + profile.profileImageUrl
//                        )
//                        Log.d(
//                            "KAKAO_API",
//                            "thumbnail image: " + profile.thumbnailImageUrl
//                        )
                    }
                })
        }
    }

    companion object {
        const val TAG: String = "LoginActivityTAG"
    }
}
