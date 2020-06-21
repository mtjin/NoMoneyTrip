package com.mtjin.nomoneytrip.di

import android.app.Application
import com.kakao.auth.*
import com.mtjin.nomoneytrip.BuildConfig
import com.mtjin.nomoneytrip.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class KoinApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        // Kakao Sdk 초기화
        KakaoSDK.init(KakaoSDKAdapter())

        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger()
            } else {
                androidLogger(Level.NONE)
            }
            androidContext(this@KoinApplication)
            modules(
                repositoryModule,
                localDataModule,
                remoteDataModule,
                viewModelModule,
                apiModule
            )
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    class KakaoSDKAdapter : KakaoAdapter() {
        override fun getSessionConfig(): ISessionConfig {
            return object : ISessionConfig {
                override fun getAuthTypes(): Array<AuthType> {
                    return arrayOf(AuthType.KAKAO_LOGIN_ALL)
                }

                override fun isUsingWebviewTimer(): Boolean {
                    return false
                }

                override fun isSecureMode(): Boolean {
                    return false
                }

                override fun getApprovalType(): ApprovalType? {
                    return ApprovalType.INDIVIDUAL
                }

                override fun isSaveFormData(): Boolean {
                    return true
                }
            }
        }

        // Application이 가지고 있는 정보를 얻기 위한 인터페이스
        override fun getApplicationConfig(): IApplicationConfig {
            return IApplicationConfig { getGlobalApplicationContext() }
        }
    }

    companion object {
        private var instance: KoinApplication? = null

        fun getGlobalApplicationContext(): KoinApplication? {
            checkNotNull(instance) { "This Application does not inherit com.kakao.GlobalApplication" }
            return instance
        }
    }

}