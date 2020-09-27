package com.mtjin.nomoneytrip.views.master_write

import android.os.Bundle
import androidx.lifecycle.Observer
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseActivity
import com.mtjin.nomoneytrip.data.master_main.MasterProduct
import com.mtjin.nomoneytrip.databinding.ActivityMasterWriteBinding
import com.mtjin.nomoneytrip.utils.EXTRA_MASTER_PRODUCT
import org.koin.androidx.viewmodel.ext.android.viewModel

class MasterWriteActivity :
    BaseActivity<ActivityMasterWriteBinding>(R.layout.activity_master_write) {
    private val viewModel: MasterWriteViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        processIntent()
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            writeSuccess.observe(this@MasterWriteActivity, Observer { success ->
                if (success) finish()
                else showToast(getString(R.string.tour_write_letter_fail_msg))
            })
            contentEmptyMsg.observe(this@MasterWriteActivity, Observer {
                showToast(getString(R.string.input_content_text))
            })
            backClick.observe(this@MasterWriteActivity, Observer {
                finish()
            })
            isLottieLoading.observe(this@MasterWriteActivity, Observer { loading ->
                if (loading) showProgressDialog()
                else hideProgressDialog()
            })
        }
    }

    private fun processIntent() {
        intent.getParcelableExtra<MasterProduct>(EXTRA_MASTER_PRODUCT)?.let { masterProduct ->
            binding.item = masterProduct
            viewModel.masterProduct = masterProduct
        }
    }
}