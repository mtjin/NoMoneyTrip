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
        processIntent()
    }

    private fun processIntent() {
        viewModel.setReservationList(args.reservation.toList())
    }
}