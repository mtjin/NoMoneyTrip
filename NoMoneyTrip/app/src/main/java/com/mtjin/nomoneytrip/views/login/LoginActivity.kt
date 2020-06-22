package com.mtjin.nomoneytrip.views.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.kakao.auth.AuthType
import com.kakao.auth.Session
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseActivity
import com.mtjin.nomoneytrip.databinding.ActivityLoginBinding
import com.mtjin.nomoneytrip.views.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        Session.getCurrentSession().addCallback(viewModel.sessionCallback)
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            loginSuccess.observe(this@LoginActivity, Observer {
                Log.i(TAG, "로그인 성공")
                val intent: Intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            })
            exception.observe(this@LoginActivity, Observer {
                Log.e(TAG, "로그인 실패", exception.value)
            })
            kakaoLogin.observe(this@LoginActivity, Observer {
                kakaoLogin.value?.addCallback(SessionCallback())
                kakaoLogin.value?.open(AuthType.KAKAO_LOGIN_ALL, this@LoginActivity)
            })
        }
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


    companion object {
        const val TAG: String = "LoginActivityTAG"
    }
}
