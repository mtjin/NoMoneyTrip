package com.mtjin.nomoneytrip.views.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.utils.fcm
import com.mtjin.nomoneytrip.utils.uuid
import com.mtjin.nomoneytrip.views.login.LoginActivity
import com.mtjin.nomoneytrip.views.login.LoginViewModel
import com.mtjin.nomoneytrip.views.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModel()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        auth = FirebaseAuth.getInstance()
        initFcmToken()
        if (auth.currentUser != null) { //로그인 되어있을시 메인화면으로
            uuid = auth.currentUser?.uid.toString()
            viewModel.updateFCM()
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 1000)
        } else {
            // 로그인 안되어있을 시 로그인 화면으로
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, 1000)
        }
    }

    private fun initFcmToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(object : OnCompleteListener<InstanceIdResult?> {
                override fun onComplete(task: Task<InstanceIdResult?>) {
                    if (!task.isSuccessful) {
                        return
                    }
                    // Get new Instance ID token
                    task.result?.let {
                        fcm = it.token
                    }
                }
            })
    }
}