package com.mtjin.nomoneytrip.data.master_write.source

import com.mtjin.nomoneytrip.data.master_main.MasterProduct
import com.mtjin.nomoneytrip.data.master_write.MasterLetter
import io.reactivex.Completable

interface MasterWriteRepository {
    fun insertMasterLetter(masterLetter: MasterLetter, masterProduct: MasterProduct) : Completable
}