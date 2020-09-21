package com.mtjin.nomoneytrip.data.master_main.source

import com.mtjin.nomoneytrip.data.master_main.MasterProduct
import io.reactivex.Completable
import io.reactivex.Single

interface MasterMainRepository {
    fun requestNewMasterProducts(): Single<List<MasterProduct>>
    fun requestAcceptedMasterProducts(): Single<List<MasterProduct>>
    fun updateReservationState(
        masterProduct: MasterProduct,
        masterState: Int//수락상태
    ): Completable

    fun sendFCM(masterProduct: MasterProduct, masterState: Int)
}