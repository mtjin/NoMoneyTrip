package com.mtjin.nomoneytrip.views.reservation_phase_first

import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentReservationPhaseFirstBinding
import com.mtjin.nomoneytrip.utils.getCurrentDay
import com.mtjin.nomoneytrip.utils.getCurrentMonth
import com.mtjin.nomoneytrip.utils.getCurrentYear
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import org.koin.androidx.viewmodel.ext.android.viewModel


class ReservationPhaseFirstFragment :
    BaseFragment<FragmentReservationPhaseFirstBinding>(R.layout.fragment_reservation_phase_first) {
    private val productArgs: ReservationPhaseFirstFragmentArgs by navArgs()
    private val viewModel: ReservationPhaseFirstViewModel by viewModel()

    override fun init() {
        binding.vm = viewModel
        processIntent()
        initCalendar()
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            showCalendar.observe(this@ReservationPhaseFirstFragment, Observer {
                if (it) binding.cvCalendar.visibility = View.VISIBLE
                else binding.cvCalendar.visibility = View.GONE
            })
        }
    }

    private fun processIntent() {
        binding.product = productArgs.product
    }

    private fun initCalendar() {
        binding.cvCalendar.state().edit()
            .setMinimumDate(
                CalendarDay.from(
                    getCurrentYear(),
                    getCurrentMonth(),
                    getCurrentDay() + 1
                )
            )
            .setCalendarDisplayMode(CalendarMode.WEEKS).commit()
    }
}