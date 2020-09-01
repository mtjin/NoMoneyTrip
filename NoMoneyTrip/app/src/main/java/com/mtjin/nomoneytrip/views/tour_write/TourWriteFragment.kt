package com.mtjin.nomoneytrip.views.tour_write

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.lifecycle.Observer
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentTourWriteBinding
import com.mtjin.nomoneytrip.utils.RC_PICK_IMAGE
import org.koin.androidx.viewmodel.ext.android.viewModel

class TourWriteFragment : BaseFragment<FragmentTourWriteBinding>(R.layout.fragment_tour_write) {

    private val viewModel: TourWriteViewModel by viewModel()

    override fun init() {
        binding.vm = viewModel
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            pickImage.observe(this@TourWriteFragment, Observer {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, RC_PICK_IMAGE)
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == RC_PICK_IMAGE) {
            binding.ivImage.setImageURI(data?.data)
        }
    }
}