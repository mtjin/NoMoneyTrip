package com.mtjin.nomoneytrip.data.master_main.source

import com.mtjin.nomoneytrip.data.master_main.MasterProduct
import io.reactivex.Single

interface MasterMainRepository {
    fun requestNewMasterProducts() : Single<List<MasterProduct>>
    fun requestAcceptedMasterProducts() : Single<List<MasterProduct>>
}