package com.mtjin.nomoneytrip.views.phone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseActivity
import com.mtjin.nomoneytrip.databinding.ActivityPhoneAuthBinding
import com.mtjin.nomoneytrip.views.login.LoginActivity
import com.mtjin.nomoneytrip.views.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class PhoneAuthActivity : BaseActivity<ActivityPhoneAuthBinding>(R.layout.activity_phone_auth) {
    private val viewModel: PhoneAuthViewModel by viewModel()
    private val callbacks by lazy {
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(phoneAuth: PhoneAuthCredential) {
                showToast("인증코드가 전송되었습니다. 60초 이내에 입력해주세요 :)")
                viewModel.authNum = phoneAuth.smsCode.toString()
                binding.etEnterCode.isEnabled = true
                binding.tvAuthNext.isEnabled = true
                binding.etPhone.isEnabled = true
                viewModel.updateAuthState(true)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                showToast("인증실패")
                binding.etPhone.isEnabled = true
                Log.d(com.mtjin.nomoneytrip.utils.TAG, p0.toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            requestAuth.observe(this@PhoneAuthActivity, Observer {
                Log.d(com.mtjin.nomoneytrip.utils.TAG, viewModel.etPhoneNum.toString())
                if (it) {
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+82" + viewModel.etPhoneNum.value.toString(), // Phone number to verify
                        60, // Timeout duration
                        TimeUnit.SECONDS, // Unit of timeout
                        this@PhoneAuthActivity, // Activity (for callback binding)
                        callbacks
                    ) // OnVerificationStateChangedCallbacks
                    binding.etPhone.isEnabled = false
                } else {
                    binding.etPhone.error = getString(R.string.please_enter_phone_err)
                }
            })

            resultAuthUser.observe(this@PhoneAuthActivity, Observer { success ->
                if (!success) {
                    showToast("인증실패")
                } else {
                    startActivity(Intent(this@PhoneAuthActivity, MainActivity::class.java))
                    showToast("인증성공")
                    finish()
                }
            })

            backClick.observe(this@PhoneAuthActivity, Observer {
                startActivity(Intent(this@PhoneAuthActivity, LoginActivity::class.java))
                finish()
            })
        }
    }

}
