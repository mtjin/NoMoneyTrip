package com.mtjin.nomoneytrip.views.tour_write

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentTourWriteBinding
import com.mtjin.nomoneytrip.utils.RC_PICK_IMAGE
import org.koin.androidx.viewmodel.ext.android.viewModel


class TourWriteFragment : BaseFragment<FragmentTourWriteBinding>(R.layout.fragment_tour_write) {

    private val viewModel: TourWriteViewModel by viewModel()
    private val args: TourWriteFragmentArgs by navArgs()

    override fun init() {
        binding.vm = viewModel
        processIntent()
        initViewModelCallback()
    }

    private fun processIntent() {
        binding.item = args.reservationProduct
        binding.rbRating.rating = args.rating
        viewModel.reservationProduct = args.reservationProduct
        viewModel.rating = args.rating
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            pickImage.observe(this@TourWriteFragment, Observer {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, RC_PICK_IMAGE)
            })

            contentEmptyMsg.observe(this@TourWriteFragment, Observer {
                showToast(getString(R.string.input_content_text))
            })

            imageEmptyMsg.observe(this@TourWriteFragment, Observer {
                showToast(getString(R.string.please_upload_image_msg))
            })

            writeSuccess.observe(this@TourWriteFragment, Observer { success ->
                if (success) findNavController().navigate(TourWriteFragmentDirections.actionTourWriteFragmentToBottomNav2())
                else showToast(getString(R.string.tour_write_upload_fail_msg))
            })
            backClick.observe(this@TourWriteFragment, Observer {
                findNavController().popBackStack()
            })
            isLottieLoading.observe(this@TourWriteFragment, Observer {loading->
                if(loading) showProgressDialog()
                else hideProgressDialog()
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == RC_PICK_IMAGE) {
            data?.data?.let {
                binding.ivImage.setImageURI(it)
                viewModel.imageUri = it
            }
        }
    }
}