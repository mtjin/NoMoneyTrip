package com.mtjin.nomoneytrip.views.email_signup

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseActivity
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.databinding.ActivityEmailSignupBinding
import com.mtjin.nomoneytrip.utils.fcm
import com.mtjin.nomoneytrip.utils.extensions.getTimestamp
import com.mtjin.nomoneytrip.utils.uuid
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
            signUpSuccess.observe(this@EmailSignUpActivity, Observer {
                auth.signOut()
                val intent: Intent = Intent(
                    this@EmailSignUpActivity,
                    EmailLoginActivity::class.java
                )
                showToast("회원가입되었습니다")
                startActivity(intent)
                finish()
            })
            isLottieLoading.observe(this@EmailSignUpActivity, Observer { loading ->
                if (loading) showProgressDialog()
                else hideProgressDialog()
            })
        }
    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this@EmailSignUpActivity) { task ->
                if (task.isSuccessful) {
                    emailLogin(email = email, password = password)
                } else {
                    showToast("회원가입 실패")
                }
            }
    }

    private fun emailLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    uuid = auth.currentUser?.uid.toString()
                    viewModel.insertUser(
                        User(
                            id = uuid,
                            name = "무전" + getTimestamp().toString()
                                .subSequence(0, 6),
                            fcm = fcm,
                            email = email,
                            pw = password,
                            image = "https://firebasestorage.googleapis.com/v0/b/nomoneytrip-63056.appspot.com/o/logo.PNG?alt=media&token=af7fe080-92fa-4fba-bab9-e9c1c3b85380"
                        )
                    )
                }
            }
    }
}
