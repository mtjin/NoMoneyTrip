package com.mtjin.nomoneytrip.module

import com.mtjin.nomoneytrip.api.ApiClient
import com.mtjin.nomoneytrip.api.ApiInterface
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val apiModule: Module = module {
    single<ApiInterface>(named("tour")) { get<Retrofit>().create(ApiInterface::class.java) }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(ApiClient.TOUR_BASE_URL)
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }

    single<GsonConverterFactory> { GsonConverterFactory.create() }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .run {
                addInterceptor(get<Interceptor>())
                build()
            }
    }

    single<Interceptor> {
        Interceptor { chain ->
            with(chain) {
                val newRequest = request().newBuilder()
                    .build()
                proceed(newRequest)
            }
        }
    }
}