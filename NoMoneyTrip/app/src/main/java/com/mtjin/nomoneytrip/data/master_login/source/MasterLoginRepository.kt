package com.mtjin.nomoneytrip.data.master_login.source

import io.reactivex.Completable

interface MasterLoginRepository {
    fun requestMasterLogin(id: String, pw: String): Completable
}