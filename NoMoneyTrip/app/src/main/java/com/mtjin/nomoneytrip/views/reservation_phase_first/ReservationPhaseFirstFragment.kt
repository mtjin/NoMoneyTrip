package com.mtjin.nomoneytrip.views.reservation_phase_first

import android.util.Log
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentReservationPhaseFirstBinding
import kotlinx.android.synthetic.main.fragment_reservation_phase_first.*

class ReservationPhaseFirstFragment :
    BaseFragment<FragmentReservationPhaseFirstBinding>(R.layout.fragment_reservation_phase_first) {
    override fun init() {
        cv_calendar.selectionManager = RangeSelectionManager(OnDaySelectedListener {
            Log.e(" CALENDAR ", "========== setSelectionManager ==========")
            Log.e(" CALENDAR ", "Selected Dates : " + cv_calendar.selectedDates.size)
            if (cv_calendar.selectedDates.size <= 0) return@OnDaySelectedListener
            Log.e(" CALENDAR ", "Selected Days : " + cv_calendar.selectedDays)
        })
    }
}