package com.mtjin.nomoneytrip.views.reservation_history


import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentReservationHistoryBinding
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

    }

    private fun initAdapter() {
        val adapter = ReservationHistoryAdapter(thisContext)
        binding.rvReservationHistories.adapter = adapter
    }
}