package com.mtjin.nomoneytrip.views.tour_history

import androidx.navigation.fragment.navArgs
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentTourHistoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TourHistoryFragment :
    BaseFragment<FragmentTourHistoryBinding>(R.layout.fragment_tour_history) {

    private val args: TourHistoryFragmentArgs by navArgs()
    private val viewModel: TourHistoryViewModel by viewModel()
    override fun init() {
        binding.vm = viewModel
        initAdapter()
        processIntent()
    }

    private fun initAdapter() {
        val adapter = TourHistoryAdapter(context = thisContext) {
            viewModel.setOnNext(true)
            viewModel.selectedReservationProduct = it
        }
        binding.rvProducts.adapter = adapter
    }

    private fun processIntent() {
        viewModel.setReservationList(args.reservation.toList())
    }
}