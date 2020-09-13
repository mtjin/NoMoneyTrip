package com.mtjin.nomoneytrip.views.tour_history

import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentTourHistoryBinding
import com.mtjin.nomoneytrip.views.dialog.RatingBottomDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TourHistoryFragment :
    BaseFragment<FragmentTourHistoryBinding>(R.layout.fragment_tour_history) {

    private val args: TourHistoryFragmentArgs by navArgs()
    private val viewModel: TourHistoryViewModel by viewModel()
    override fun init() {
        binding.vm = viewModel
        initAdapter()
        processIntent()
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            goTourWrite.observe(this@TourHistoryFragment, Observer { reservationProduct ->
                val dialog =
                    RatingBottomDialogFragment.newInstance(
                        ratingClick = { rating ->
                            showToast(rating.toString())
                            findNavController().navigate(
                                TourHistoryFragmentDirections.actionTourHistoryFragmentToTourWriteFragment(
                                    reservationProduct, rating
                                )
                            )
                        })
                dialog.show(requireActivity().supportFragmentManager, dialog.tag)
            })

            backClick.observe(this@TourHistoryFragment, Observer {
                findNavController().popBackStack()
            })
        }
    }

    private fun initAdapter() {
        viewModel.setOnNext(false)
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