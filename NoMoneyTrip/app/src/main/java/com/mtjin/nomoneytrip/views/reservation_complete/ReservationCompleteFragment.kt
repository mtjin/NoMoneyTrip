package com.mtjin.nomoneytrip.views.reservation_complete

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentReservationCompleteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReservationCompleteFragment :
    BaseFragment<FragmentReservationCompleteBinding>(R.layout.fragment_reservation_complete) {

    private val viewModel: ReservationCompleteViewModel by viewModel()
    private val args: ReservationCompleteFragmentArgs by navArgs()

    override fun init() {
        binding.vm = viewModel
        processIntent()
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            goHome.observe(this@ReservationCompleteFragment, Observer {
                findNavController().navigate(
                    ReservationCompleteFragmentDirections.actionReservationCompleteFragmentToBottomNav3()
                )
            })

            goReservationDetail.observe(this@ReservationCompleteFragment, Observer {
                //TODO :: 상세화면생기면 구현
            })

            backClick.observe(this@ReservationCompleteFragment, Observer {
                findNavController().popBackStack()
            })
        }
    }

    private fun processIntent() {
        viewModel.reservationId = args.reservation.id
    }
}