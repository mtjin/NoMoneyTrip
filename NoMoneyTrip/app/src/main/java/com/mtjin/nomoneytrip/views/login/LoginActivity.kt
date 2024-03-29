package com.mtjin.nomoneytrip.views.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.firebase.ktx.Firebase
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
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.databinding.ActivityLoginBinding
import com.mtjin.nomoneytrip.utils.USER
import com.mtjin.nomoneytrip.utils.fcm
import com.mtjin.nomoneytrip.utils.extensions.getTimestamp
import com.mtjin.nomoneytrip.utils.uuid
import com.mtjin.nomoneytrip.views.email_login.EmailLoginActivity
import com.mtjin.nomoneytrip.views.email_signup.EmailSignUpActivity
import com.mtjin.nomoneytrip.views.main.MainActivity
import com.mtjin.nomoneytrip.views.master_login.MasterLoginActivity
import com.mtjin.nomoneytrip.views.phone.PhoneAuthActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel: LoginViewModel by viewModel()
    private lateinit var auth: FirebaseAuth

    // 세션 콜백 구현
    private val sessionCallback: ISessionCallback = object : ISessionCallback {
        override fun onSessionOpened() {
            Log.i(TAG, getString(R.string.login_success_msg))
        }

        override fun onSessionOpenFailed(exception: KakaoException) {
            Log.e(TAG, getString(R.string.login_fail_msg), exception)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        Session.getCurrentSession().addCallback(sessionCallback)
        initViewModelCallback()
        initFcmToken()
        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            kakaoLogin.observe(this@LoginActivity, Observer {
                kakaoLogin.value?.addCallback(SessionCallback())
                kakaoLogin.value?.open(AuthType.KAKAO_LOGIN_ALL, this@LoginActivity)
            })

            goEmailSignUp.observe(this@LoginActivity, Observer {
                startActivity(Intent(this@LoginActivity, EmailSignUpActivity::class.java))
            })

            goEmailLogin.observe(this@LoginActivity, Observer {
                startActivity(Intent(this@LoginActivity, EmailLoginActivity::class.java))
            })

            goMasterLogin.observe(this@LoginActivity, Observer {
                startActivity(Intent(this@LoginActivity, MasterLoginActivity::class.java))
            })

            insertUserResult.observe(this@LoginActivity, Observer { success ->
                if (!success) {
                    showToast(getString(R.string.error_excution_msg))
                } else {
                    Firebase.database.reference.child(USER).child(uuid)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(error: DatabaseError) {
                                showToast(getString(R.string.error_excution_msg))
                            }

                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()) {
                                    snapshot.getValue(User::class.java)?.let { user ->
                                        if (user.tel.isNotBlank()) goMain()
                                        else goPhoneAuth()
                                    }
                                }
                            }
                        })
                }
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

    override fun onDestroy() {
        super.onDestroy()
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback)
    }

    inner class SessionCallback : ISessionCallback {
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
                        val email: String = "" + result.id + "@mujeon.com"
                        val password: String = "111111"
                        //구글이메일 로그인
                        var photoUrl = result.kakaoAccount.profile.profileImageUrl
                        if (photoUrl == null) photoUrl = ""
                        googleAuth(email, password, photoUrl)
                    }
                })
        }

        fun googleAuth(email: String, password: String, image: String) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this@LoginActivity) { task ->
                    if (task.isSuccessful) {
                        uuid = auth.currentUser?.uid.toString()
                        viewModel.updateFCM()
                    } else {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this@LoginActivity) { task ->
                                if (task.isSuccessful) {
                                    auth.signInWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(this@LoginActivity) { task ->
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
                                                        image = image
                                                    )
                                                )
                                            } else {
                                                showToast(getString(R.string.login_fail_msg))
                                                viewModel.hideProgress()
                                            }
                                        }
                                } else {
                                    showToast(getString(R.string.login_fail_msg))
                                    viewModel.hideProgress()
                                }
                            }
                    }
                }
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

    fun goPhoneAuth() {
        startActivity(Intent(this, PhoneAuthActivity::class.java))
        viewModel.hideProgress()
    }

    fun goMain() {
        startActivity(Intent(this, MainActivity::class.java))
        showToast("로그인 성공")
        viewModel.hideProgress()
        finish()
    }

    companion object {
        const val TAG: String = "LoginActivityTAG"
    }
}
