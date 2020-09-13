package com.mtjin.nomoneytrip.views.reservation_history


import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentReservationHistoryBinding
import com.mtjin.nomoneytrip.utils.getTimestamp
import com.mtjin.nomoneytrip.views.dialog.BottomDialogFragment
import com.mtjin.nomoneytrip.views.dialog.RatingBottomDialogFragment
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

            isLottieLoading.observe(this@ReservationHistoryFragment, Observer { loading ->
                if (loading) showProgressDialog()
                else hideProgressDialog()
            })
        }
    }

    private fun initAdapter() {
        val adapter = ReservationHistoryAdapter(thisContext,
            { reservationHistory ->
                if (reservationHistory.reservation.endDateTimestamp >= getTimestamp()) { // 예약변경
                    showToast(getString(R.string.update_later_text))
                } else { //봉사인증
                    showToast(getString(R.string.update_later_text))
                }
            }, { reservationHistory ->
                when {
                    reservationHistory.reservation.endDateTimestamp >= getTimestamp() -> {
                        val dialog =
                            BottomDialogFragment.newInstance(
                                question = "예약을 취소 하시겠습니까?",
                                itemClick = {
                                    if (it) viewModel.deleteReservation(reservationHistory.reservation)
                                })
                        dialog.show(requireActivity().supportFragmentManager, dialog.tag)
                    }
                    else -> {
                        if (reservationHistory.reservation.reviewed) showToast(getString(R.string.aleready_reviewed_product_msg)) //이미 리뷰 남긴거
                        else {
                            val dialog =
                                RatingBottomDialogFragment.newInstance(
                                    ratingClick = { rating ->
                                        showToast(rating.toString())
                                        findNavController().navigate(
                                            ReservationHistoryFragmentDirections.actionBottomNav3ToTourWriteFragment(
                                                reservationHistory, rating
                                            )
                                        )
                                    })
                            dialog.show(requireActivity().supportFragmentManager, dialog.tag)
                        }
                    }
                }
            })
        binding.rvReservationHistories.adapter = adapter
    }
}