package com.mtjin.nomoneytrip.views.reservation_detail

import androidx.navigation.fragment.navArgs
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentReservationDetailBinding

class ReservationDetailFragment :
    BaseFragment<FragmentReservationDetailBinding>(R.layout.fragment_reservation_detail) {
    private val reservationProduct: ReservationDetailFragmentArgs by navArgs()
    override fun init() {
        processIntent()
    }

    private fun processIntent() {
        binding.item = reservationProduct.reservationProduct
    }
}