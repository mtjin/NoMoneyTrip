package com.mtjin.nomoneytrip.views.email_signup

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseActivity
import com.mtjin.nomoneytrip.databinding.ActivityEmailSignupBinding
import com.mtjin.nomoneytrip.views.email_login.EmailLoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmailSignUpActivity :
    BaseActivity<ActivityEmailSignupBinding>(R.layout.activity_email_signup) {
    private val viewModel: EmailSignUpViewModel by viewModel()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initViewModelCallback()
        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            isEmailEmpty.observe(this@EmailSignUpActivity, Observer {
                binding.etEmail.error = "이메일을 입력해주세요"
            })
            isPwEmpty.observe(this@EmailSignUpActivity, Observer {
                binding.etPw.error = "비밀번호를 입력해주세요"
            })
            isPwConfirmEmpty.observe(this@EmailSignUpActivity, Observer {
                binding.etPwConfirm.error = "비밀번호 확인을 입력해주세요"
            })
            pwNotMatch.observe(this@EmailSignUpActivity, Observer {
                showToast("비밀번호가 일치하지 않습니다.")
            })
            signUp.observe(this@EmailSignUpActivity, Observer {
                signUp(email.value!!, pw.value!!)
            })
        }
    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this@EmailSignUpActivity) { task ->
                if (task.isSuccessful) {
                    val intent: Intent = Intent(
                        this@EmailSignUpActivity,
                        EmailLoginActivity::class.java
                    )
                    showToast("회원가입되었습니다")
                    viewModel.hideProgress()
                    startActivity(intent)
                    finish()
                } else {
                    showToast("회원가입 실패")
                    viewModel.hideProgress()
                }
            }
    }
}
