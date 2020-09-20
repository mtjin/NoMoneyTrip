package com.mtjin.nomoneytrip.data.phone.source

import io.reactivex.Completable

interface PhoneAuthRepository {
    fun updateUserTel(tel: String) : Completable
}