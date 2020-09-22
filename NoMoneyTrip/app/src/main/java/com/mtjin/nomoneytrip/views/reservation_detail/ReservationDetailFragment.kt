package com.mtjin.nomoneytrip.views.reservation_detail

import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentReservationDetailBinding
import com.mtjin.nomoneytrip.utils.getMyColor
import com.mtjin.nomoneytrip.views.dialog.YesNoDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReservationDetailFragment :
    BaseFragment<FragmentReservationDetailBinding>(R.layout.fragment_reservation_detail) {
    private val reservationProduct: ReservationDetailFragmentArgs by navArgs()
    private val viewModel: ReservationDetailViewModel by viewModel()
    override fun init() {
        binding.vm = viewModel
        processIntent()
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            updateReservationCancelSuccessMsg.observe(
                this@ReservationDetailFragment,
                Observer { success ->
                    if (success) {
                        binding.tvReservationCancel.visibility = View.GONE
                        binding.tvState.text = "예약이 취소되었습니다."
                        binding.tvState.setTextColor(thisContext.getMyColor(R.color.colorRedEF4550))
                        showToast(getString(R.string.reservation_cancel_success_msg))
                    } else showToast(getString(R.string.reservation_cancel_fail_tmsg))
                })

            onClickCancelReservation.observe(this@ReservationDetailFragment, Observer {
                val dialog =
                    YesNoDialogFragment.getInstance(yesClick = {
                        if (it) viewModel.updateReservationCancel(reservationProduct = reservationProduct.reservationProduct)
                    })
                dialog.show(requireActivity().supportFragmentManager, dialog.tag)
            })

            isLottieLoading.observe(this@ReservationDetailFragment, Observer { loading ->
                if (loading) showProgressDialog()
                else hideProgressDialog()
            })
        }
    }

    private fun processIntent() {
        binding.item = reservationProduct.reservationProduct
    }
}