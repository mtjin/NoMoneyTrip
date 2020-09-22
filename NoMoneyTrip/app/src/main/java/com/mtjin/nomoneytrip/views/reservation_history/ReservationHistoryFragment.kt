package com.mtjin.nomoneytrip.views.reservation_history


import android.content.Intent
import android.net.Uri
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentReservationHistoryBinding
import com.mtjin.nomoneytrip.utils.getTimestamp
import com.mtjin.nomoneytrip.views.dialog.RatingBottomDialogFragment
import com.mtjin.nomoneytrip.views.dialog.YesNoDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReservationHistoryFragment :
    BaseFragment<FragmentReservationHistoryBinding>(R.layout.fragment_reservation_history) {
    private val viewModel: ReservationHistoryViewModel by viewModel()

    override fun init() {
        binding.vm = viewModel
        initAdapter()
        initViewModelCallback()
        requestReservations()
    }

    private fun requestReservations() {
        viewModel.requestReservations()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            deleteReservationSuccessMsg.observe(
                this@ReservationHistoryFragment,
                Observer { success ->
                    if (success) showToast(getString(R.string.reservation_cancel_success_msg))
                    else showToast(getString(R.string.reservation_cancel_fail_tmsg))
                })

            updateReservationCancelSuccessMsg.observe(
                this@ReservationHistoryFragment,
                Observer { success ->
                    if (success) showToast(getString(R.string.reservation_cancel_success_msg))
                    else showToast(getString(R.string.reservation_cancel_fail_tmsg))
                })

            isLottieLoading.observe(this@ReservationHistoryFragment, Observer { loading ->
                if (loading) showProgressDialog()
                else hideProgressDialog()
            })
        }
    }

    private fun initAdapter() {
        val adapter = ReservationHistoryAdapter(thisContext,
            leftClick = {   //문의
                val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + it.product.phone))
                thisContext.startActivity(callIntent)
            }, rightClick = { reservationHistory ->
                when {
                    reservationHistory.reservation.masterState == 2 && reservationHistory.reservation.startDateTimestamp >= getTimestamp() -> { //이장님예약수락상태 : 예약취소
                        val dialog =
                            YesNoDialogFragment.getInstance(yesClick = {
                                if (it) viewModel.updateReservationCancel(reservationProduct = reservationHistory)
                            })
                        dialog.show(requireActivity().supportFragmentManager, dialog.tag)
                    }
                    reservationHistory.reservation.endDateTimestamp < getTimestamp() -> {//여행완료상태 : 리뷰
                        if (reservationHistory.reservation.reviewed) showToast(getString(R.string.aleready_reviewed_product_msg)) //이미 리뷰 남긴거
                        else {
                            val dialog =
                                RatingBottomDialogFragment.newInstance(
                                    ratingClick = { rating ->
                                        findNavController().navigate(
                                            ReservationHistoryFragmentDirections.actionBottomNav3ToTourWriteFragment(
                                                reservationHistory, rating
                                            )
                                        )
                                    })
                            dialog.show(requireActivity().supportFragmentManager, dialog.tag)
                        }
                    }
                    else -> { //여행 접수 상태 : 예약취소
                        val dialog =
                            YesNoDialogFragment.getInstance(yesClick = {
                                if (it) viewModel.updateReservationCancel(reservationHistory)
                            })
                        dialog.show(requireActivity().supportFragmentManager, dialog.tag)
                    }
                }
            }, layoutClick = {

            })
        binding.rvReservationHistories.adapter = adapter
    }
}