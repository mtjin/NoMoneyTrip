package com.mtjin.nomoneytrip.views.email_signup

import android.os.Bundle
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseActivity
import com.mtjin.nomoneytrip.databinding.ActivityEmailSignupBinding

class EmailSignupActivity :
    BaseActivity<ActivityEmailSignupBinding>(R.layout.activity_email_signup) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_signup)
    }
}
