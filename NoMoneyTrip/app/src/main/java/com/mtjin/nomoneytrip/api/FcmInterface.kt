package com.mtjin.nomoneytrip.api

import com.mtjin.nomoneytrip.service.notification.NotificationBody
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface FcmInterface {
    @POST("fcm/send")
    fun sendNotification(
        @Body notification: NotificationBody
    ): Single<ResponseBody>
}