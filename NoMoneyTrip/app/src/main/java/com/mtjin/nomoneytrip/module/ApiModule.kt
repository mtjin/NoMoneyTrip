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
    single<ApiInterface>(named(TOUR_NAMED)) { get<Retrofit>(named(TOUR_NAMED)).create(ApiInterface::class.java) }
    single<FcmInterface>(named(FCM_NAMED)) { get<Retrofit>(named(FCM_NAMED)).create(FcmInterface::class.java) }

    single<Retrofit>(named(FCM_NAMED)) {
        Retrofit.Builder()
            .baseUrl(ApiClient.FCM_URL)
            .client(get(named(FCM_NAMED)))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }

    single<Retrofit>(named(TOUR_NAMED)) {
        Retrofit.Builder()
            .baseUrl(ApiClient.TOUR_BASE_URL)
            .client(get(named(TOUR_NAMED)))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }


    single<GsonConverterFactory> { GsonConverterFactory.create() }

    single<OkHttpClient>(named(TOUR_NAMED)) {
        OkHttpClient.Builder()
            .run {
                addInterceptor(get<Interceptor>(named(TOUR_NAMED)))
                build()
            }
    }

    single<OkHttpClient>(named(FCM_NAMED)) {
        OkHttpClient.Builder()
            .run {
                addInterceptor(get<Interceptor>(named(FCM_NAMED)))
                build()
            }
    }

    single<Interceptor>(named(FCM_NAMED)) {
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



    single<Interceptor>(named(TOUR_NAMED)) {
        Interceptor { chain ->
            with(chain) {
                val newRequest = request().newBuilder()
                    .build()
                proceed(newRequest)
            }
        }
    }
}