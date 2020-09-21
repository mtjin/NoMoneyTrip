package com.mtjin.nomoneytrip.views.master_main

import android.os.Bundle
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseActivity
import com.mtjin.nomoneytrip.databinding.ActivityMasterMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MasterMainActivity : BaseActivity<ActivityMasterMainBinding>(R.layout.activity_master_main) {
    private val viewModel: MasterMainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        viewModel.requestNewMasterProducts()
        viewModel.requestAcceptedMasterProducts()
        initAdapter()
    }

    private fun initAdapter() {
        val newAdapter = MasterMainAdapter(this, leftClick = {}, rightClick = {})
        val acceptedAdapter = MasterMainAdapter(this, leftClick = {}, rightClick = {})
        binding.rvNewApplicants.adapter = newAdapter
        binding.rvAcceptedApplicants.adapter = acceptedAdapter
    }
}