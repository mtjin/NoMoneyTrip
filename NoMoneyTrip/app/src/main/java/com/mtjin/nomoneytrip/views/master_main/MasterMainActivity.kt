package com.mtjin.nomoneytrip.views.master_main

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseActivity
import com.mtjin.nomoneytrip.databinding.ActivityMasterMainBinding
import com.mtjin.nomoneytrip.utils.EXTRA_MASTER_PRODUCT
import com.mtjin.nomoneytrip.views.master_write.MasterWriteActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MasterMainActivity : BaseActivity<ActivityMasterMainBinding>(R.layout.activity_master_main) {
    private val viewModel: MasterMainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initViewModelCallback()
        initAdapter()
    }

    override fun onStart() {
        viewModel.requestNewMasterProducts()
        viewModel.requestAcceptedMasterProducts()
        super.onStart()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            isLottieLoading.observe(this@MasterMainActivity, Observer { loading ->
                if (loading) showProgressDialog()
                else hideProgressDialog()
            })
            onClickAlarm.observe(this@MasterMainActivity, Observer {
                showToast("추후 업데이트 예정")
            })
        }
    }

    private fun initAdapter() {
        val newAdapter = MasterMainAdapter(this, leftClick = {
            viewModel.updateReservationState(masterProduct = it, masterState = 1) //예약거절
        }, rightClick = {
            viewModel.updateReservationState(masterProduct = it, masterState = 2) //예약수락
        }, messageClick = {
        })

        val acceptedAdapter =
            MasterMainAdapter(this, leftClick = {}, rightClick = {}, messageClick = {
                if (it.reservation.masterLetter) {
                    showToast(getString(R.string.already_send_letter_text))
                } else {
                    val masterWriteIntent = Intent(this, MasterWriteActivity::class.java)
                    masterWriteIntent.putExtra(EXTRA_MASTER_PRODUCT, it)
                    startActivity(masterWriteIntent)
                }
            })
        binding.rvNewApplicants.adapter = newAdapter
        binding.rvAcceptedApplicants.adapter = acceptedAdapter
    }
}