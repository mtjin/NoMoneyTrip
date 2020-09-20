package com.mtjin.nomoneytrip.views.phone

import android.os.Bundle
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseActivity
import com.mtjin.nomoneytrip.databinding.ActivityPhoneAuthBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhoneAuthActivity : BaseActivity<ActivityPhoneAuthBinding>(R.layout.activity_phone_auth) {
    private val viewModel: PhoneAuthViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
    }
}
