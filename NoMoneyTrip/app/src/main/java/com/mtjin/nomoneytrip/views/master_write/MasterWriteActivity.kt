package com.mtjin.nomoneytrip.views.master_write

import android.os.Bundle
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseActivity
import com.mtjin.nomoneytrip.databinding.ActivityMasterWriteBinding

class MasterWriteActivity :
    BaseActivity<ActivityMasterWriteBinding>(R.layout.activity_master_write) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        processIntent()
    }

    private fun processIntent() {
        
    }
}