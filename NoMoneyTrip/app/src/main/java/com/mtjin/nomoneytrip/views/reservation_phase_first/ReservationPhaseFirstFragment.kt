package com.mtjin.nomoneytrip.views.reservation_phase_first

import android.util.Log
import androidx.navigation.fragment.navArgs
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentReservationPhaseFirstBinding
import kotlinx.android.synthetic.main.fragment_reservation_phase_first.*

class ReservationPhaseFirstFragment :
    BaseFragment<FragmentReservationPhaseFirstBinding>(R.layout.fragment_reservation_phase_first) {
    private val productArgs: ReservationPhaseFirstFragmentArgs by navArgs()
    override fun init() {
        processIntent()
        initCalendar()
    }

    private fun processIntent() {
        binding.product = productArgs.product
    }

    private fun initCalendar() {
        binding.cvCalendar.selectionManager = RangeSelectionManager(OnDaySelectedListener {
            Log.e(" CALENDAR ", "========== setSelectionManager ==========")
            Log.e(" CALENDAR ", "Selected Dates : " + cv_calendar.selectedDates.size)
            if (cv_calendar.selectedDates.size <= 0) return@OnDaySelectedListener
            Log.e(" CALENDAR ", "Selected Days : " + cv_calendar.selectedDays)
        })
    }
}