package com.mtjin.nomoneytrip.views.reservation_phase_first

import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.data.reservation.Reservation
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
        viewModel.requestReservations(product = productArgs.product)
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            showCalendar.observe(this@ReservationPhaseFirstFragment, Observer {
                if (it) {
                    binding.cvCalendar.visibility = View.VISIBLE
                    binding.viewCalendar.visibility = View.VISIBLE
                    binding.ivCalendarShow.setImageResource(R.drawable.ic_scroll_up)
                } else {
                    binding.cvCalendar.visibility = View.GONE
                    binding.viewCalendar.visibility = View.GONE
                    binding.ivCalendarShow.setImageResource(R.drawable.ic_scroll_down)
                }
            })
            option1.observe(this@ReservationPhaseFirstFragment, Observer {
                binding.cbOption1.isChecked = it
                viewModel.selectedOption = binding.tvOption1.text.toString()
            })

            option2.observe(this@ReservationPhaseFirstFragment, Observer {
                binding.cbOption2.isChecked = it
                viewModel.selectedOption = binding.tvOption2.text.toString()
            })

            goReservation.observe(this@ReservationPhaseFirstFragment, Observer {
                val reservation = Reservation(
                    id = "",//Reservation DataSource 에서 파베 키값으로 넣어줌
                    userId = uuid,
                    option = viewModel.selectedOption,
                    num = viewModel.num.value!!,
                    productId = productArgs.product.id,
                    startDateTimestamp = viewModel.startDateTimestamp,
                    endDateTimestamp = viewModel.endDateTimestamp
                )
                findNavController().navigate(
                    ReservationPhaseFirstFragmentDirections.actionReservationFirstFragmentToReservationFragment(
                        productArgs.product,
                        reservation
                    )
                )
            })

            dateList.observe(this@ReservationPhaseFirstFragment, Observer { reservations ->
                initDisableDates(reservations)
            })

            backClick.observe(this@ReservationPhaseFirstFragment, Observer {
                findNavController().popBackStack()
            })
        }
    }

    private fun initDisableDates(reservations: List<Reservation>) {
        val calList = ArrayList<CalendarDay>()
        for (reservation in reservations) { //하루당 86400000
            val res = reservation.copy()
            res.run {
                while (endDateTimestamp > startDateTimestamp) {
                    endDateTimestamp -= TIMESTAMP_PER_DAY
                    calList.add(
                        CalendarDay.from(
                            endDateTimestamp.convertTimestampToYear(),
                            endDateTimestamp.convertTimestampToMonth(),
                            endDateTimestamp.convertTimestampToDay()
                        )
                    )
                }
            }
        }
        for (calDay in calList) {
            binding.cvCalendar.addDecorators(CurrentDayDecorator(requireActivity(), calDay))
        }
    }

    private fun processIntent() {
        binding.product = productArgs.product
    }

    private fun initCalendar() {
        viewModel.isDateSelected = false
        viewModel.setAllSelected()
        binding.cvCalendar.state().edit()
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .setMinimumDate(
                CalendarDay.from(
                    getCurrentYear(),
                    getCurrentMonth(),
                    getCurrentDay() + 1
                )
            ).commit()

        binding.cvCalendar.setOnRangeSelectedListener { widget, dates ->
            if (!viewModel.checkDatesAvailable(dates)) {
                showToast(getString(R.string.date_can_not_selected_msg))
                binding.cvCalendar.clearSelection()
                viewModel.initDateRange()
                return@setOnRangeSelectedListener
            }
            viewModel.setStartDateTimestamp(dates[0])
            if (dates.size == 0) {
                viewModel.setEndDateTimestamp(dates[dates.size])
            } else if (dates.size != 1) {
                viewModel.setEndDateTimestamp(dates[dates.size - 1])
            }
            viewModel.setDateRange()
        }
        binding.cvCalendar.setOnDateChangedListener { widget, date, selected ->
            val calList = ArrayList<CalendarDay>()
            calList.add(date)
            when {
                !selected -> {
                    viewModel.initDateRange()
                    viewModel.isDateSelected = false
                    viewModel.setAllSelected()
                }
                !viewModel.checkDatesAvailable(calList) -> {
                    showToast(getString(R.string.date_can_not_selected_msg))
                    binding.cvCalendar.clearSelection()
                    viewModel.initDateRange()
                }
                else -> {
                    viewModel.setStartDateTimestamp(date)
                    viewModel.setEndDateTimestamp(date)
                    viewModel.isDateSelected = selected
                    viewModel.setAllSelected()
                    viewModel.setSingleDateRange()
                }
            }
        }
    }
}