package com.mtjin.nomoneytrip.data.master_login.source

import io.reactivex.Completable

interface MasterLoginRepository {
    var masterIdInput: String
    var masterPwInput: String
    fun requestMasterLogin(id: String, pw: String): Completable
    fun updateFCM()
}