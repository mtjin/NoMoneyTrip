package com.mtjin.nomoneytrip.views.master_login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseActivity
import com.mtjin.nomoneytrip.databinding.ActivityMasterLoginBinding
import com.mtjin.nomoneytrip.views.master_main.MasterMainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MasterLoginActivity :
    BaseActivity<ActivityMasterLoginBinding>(R.layout.activity_master_login) {
    private val viewModel: MasterLoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            isEmailEmpty.observe(this@MasterLoginActivity, Observer {
                binding.etEmail.error = "이메일을 입력해주세요"
            })
            isPwEmpty.observe(this@MasterLoginActivity, Observer {
                binding.etPw.error = "비밀번호를 입력해주세요"
            })
            login.observe(this@MasterLoginActivity, Observer {
                viewModel.requestMasterLogin()
            })
            backCLick.observe(this@MasterLoginActivity, Observer {
                finish()
            })
            loginSuccess.observe(this@MasterLoginActivity, Observer { success ->
                if (!success) showToast("아이디 또는 비밀번호가 일치하지 않습니다")
                else startActivity(Intent(this@MasterLoginActivity, MasterMainActivity::class.java))
            })
        }
    }
}