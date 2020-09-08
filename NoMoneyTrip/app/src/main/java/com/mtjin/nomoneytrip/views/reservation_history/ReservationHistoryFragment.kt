package com.mtjin.nomoneytrip.views.reservation_history


import androidx.lifecycle.Observer
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentReservationHistoryBinding
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
            deleteReservationSuccessMsg.observe(this@ReservationHistoryFragment, Observer {success->
                if(success)  showToast(getString(R.string.reservation_cancel_success_msg))
                else showToast(getString(R.string.reservation_cancel_fail_tmsg))
            })
        }
    }

    private fun initAdapter() {
        val adapter = ReservationHistoryAdapter(thisContext,
            {
                if (it.reservation.state) { // 예약변경
                    //TODO :: 추후 업데이트 예정
                } else { //봉사인증

                }
            }, { reservationHistory ->
                if (reservationHistory.reservation.state) { //예약 취소
                    val dialog =
                        BottomDialogFragment.newInstance(question = "예약을 취소 하시겠습니까?", itemClick = {
                            if (it) viewModel.deleteReservation(reservationHistory.reservation)
                        })
                    dialog.show(requireActivity().supportFragmentManager, dialog.tag)
                } else {// 봉사리뷰

                }
            })
        binding.rvReservationHistories.adapter = adapter
    }
}