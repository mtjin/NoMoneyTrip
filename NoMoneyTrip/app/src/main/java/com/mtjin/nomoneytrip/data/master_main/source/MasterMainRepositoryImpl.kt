package com.mtjin.nomoneytrip.data.master_main.source

import com.google.firebase.database.DatabaseReference
import com.mtjin.nomoneytrip.data.master_main.MasterProduct
import io.reactivex.Single

class MasterMainRepositoryImpl(private val database: DatabaseReference) : MasterMainRepository {
    override fun requestNewMasterProducts(): Single<List<MasterProduct>> {
        TODO("Not yet implemented")
    }

    override fun requestAcceptedMasterProducts(): Single<List<MasterProduct>> {
        TODO("Not yet implemented")
    }
}