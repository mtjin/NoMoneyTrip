package com.mtjin.nomoneytrip.views.reservation_phase_first

import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentReservationPhaseFirstBinding
import com.mtjin.nomoneytrip.utils.*
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
                if (it) {
                    binding.cvCalendar.visibility = View.VISIBLE
                    binding.ivCalendarShow.setImageResource(R.drawable.ic_scroll_up)
                } else {
                    binding.cvCalendar.visibility = View.GONE
                    binding.ivCalendarShow.setImageResource(R.drawable.ic_scroll_down)
                }
            })
            allSelected.observe(this@ReservationPhaseFirstFragment, Observer { isAllSelected ->
                if (isAllSelected) {
                    binding.tvReserve.setBackgroundColor(
                        requireActivity().getMyColor(
                            R.color.colorOrangeF79256
                        )
                    )
                } else {
                    binding.tvReserve.setBackgroundColor(
                        requireActivity().getMyColor(
                            R.color.colorGrayC8C8
                        )
                    )
                }
            })
            option1.observe(this@ReservationPhaseFirstFragment, Observer {
                binding.cbOption1.isChecked = it
            })

            option2.observe(this@ReservationPhaseFirstFragment, Observer {
                binding.cbOption2.isChecked = it
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
            .setCalendarDisplayMode(CalendarMode.WEEKS)
            .commit()

        binding.cvCalendar.setOnRangeSelectedListener { widget, dates ->
            Log.d(TAG, "ReservationPhaseFirstFragment DATES-> $dates")
            Log.d(TAG, "ReservationPhaseFirstFragment  YEAR-> " + dates[0].year.toString())
            Log.d(TAG, "ReservationPhaseFirstFragment  MONTH-> " + dates[0].month.toString())
            Log.d(TAG, "ReservationPhaseFirstFragment  DAY-> " + dates[0].day.toString())
            Log.d(
                TAG,
                "ReservationPhaseFirstFragment  IS_SELCTED-> " + binding.cvCalendar.isSelected.toString()
            )
        }
        binding.cvCalendar.setOnDateChangedListener { widget, date, selected ->
            Log.d(TAG, "is Selected -> $selected")
            Log.d(TAG, "is Selected -> $date")
            viewModel.isDateSelected = selected
            viewModel.checkAllSelected()
        }
    }
}