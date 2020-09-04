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
                binding.etEmail.error = getString(R.string.enter_email_text)
            })
            isPwEmpty.observe(this@EmailSignUpActivity, Observer {
                binding.etPw.error = getString(R.string.enter_password_text)
            })
            isPwConfirmEmpty.observe(this@EmailSignUpActivity, Observer {
                binding.etPwConfirm.error = getString(R.string.enter_password_confirm_text)
            })
            pwNotMatch.observe(this@EmailSignUpActivity, Observer {
                showToast(getString(R.string.password_confirm_not_same_text))
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
