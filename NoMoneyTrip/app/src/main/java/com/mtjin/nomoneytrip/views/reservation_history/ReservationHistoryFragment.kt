package com.mtjin.nomoneytrip.views.reservation_history


import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentReservationHistoryBinding
import com.mtjin.nomoneytrip.utils.getTimestamp
import com.mtjin.nomoneytrip.views.dialog.BottomDialogFragment
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
        }
    }

    private fun initAdapter() {
        val adapter = ReservationHistoryAdapter(thisContext,
            { reservationHistory ->
                if (reservationHistory.reservation.endDateTimestamp >= getTimestamp()) { // 예약변경
                    //TODO :: 추후 업데이트 예정
                } else { //봉사인증

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
                        else findNavController().navigate(
                            ReservationHistoryFragmentDirections.actionBottomNav3ToTourWriteFragment(
                                reservationHistory
                            )
                        )
                    }
                }
            })
        binding.rvReservationHistories.adapter = adapter
    }
}