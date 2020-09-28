package com.mtjin.nomoneytrip.module

import com.mtjin.nomoneytrip.api.ApiClient
import com.mtjin.nomoneytrip.api.ApiInterface
import com.mtjin.nomoneytrip.api.FcmInterface
import com.mtjin.nomoneytrip.utils.FCM_KEY
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val apiModule: Module = module {
    single<ApiInterface>(named("tour")) { get<Retrofit>(named("tour")).create(ApiInterface::class.java) }
    single<FcmInterface>(named("fcm")) { get<Retrofit>(named("fcm")).create(FcmInterface::class.java) }

    single<Retrofit>(named("fcm")) {
        Retrofit.Builder()
            .baseUrl(ApiClient.FCM_URL)
            .client(get(named("fcm")))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }

    single<Retrofit>(named("tour")) {
        Retrofit.Builder()
            .baseUrl(ApiClient.TOUR_BASE_URL)
            .client(get(named("tour")))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }


    single<GsonConverterFactory> { GsonConverterFactory.create() }

    single<OkHttpClient>(named("tour")) {
        OkHttpClient.Builder()
            .run {
                addInterceptor(get<Interceptor>(named("tour")))
                build()
            }
    }

    single<OkHttpClient>(named("fcm")) {
        OkHttpClient.Builder()
            .run {
                addInterceptor(get<Interceptor>(named("fcm")))
                build()
            }
    }

    single<Interceptor>(named("fcm")) {
        Interceptor { chain ->
            with(chain) {
                val newRequest = request().newBuilder()
                    .addHeader("Authorization", "key=$FCM_KEY")
                    .addHeader("Content-Type", "application/json")
                    .build()
                proceed(newRequest)
            }
        }
    }



    single<Interceptor>(named("tour")) {
        Interceptor { chain ->
            with(chain) {
                val newRequest = request().newBuilder()
                    .build()
                proceed(newRequest)
            }
        }
    }
}