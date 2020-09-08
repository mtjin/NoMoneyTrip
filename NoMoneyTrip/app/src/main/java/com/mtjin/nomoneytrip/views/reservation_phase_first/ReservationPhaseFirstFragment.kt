package com.mtjin.nomoneytrip.views.reservation_phase_first

import android.util.Log
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
            viewModel.startDateTimestamp = convertDateToTimestamp(
                _year = dates[0].year,
                _month = dates[0].month,
                _day = dates[0].day
            )
            if (dates.size == 1) {
                viewModel.endDateTimestamp = convertDateToTimestamp(
                    _year = dates[0].year,
                    _month = dates[0].month,
                    _day = dates[0].day
                )
            } else {
                viewModel.endDateTimestamp = convertDateToTimestamp(
                    _year = dates[dates.size - 1].year,
                    _month = dates[dates.size - 1].month,
                    _day = dates[dates.size - 1].day
                )
            }
        }
        binding.cvCalendar.setOnDateChangedListener { widget, date, selected ->
            Log.d(TAG, "is Selected -> $selected")
            Log.d(TAG, "is Selected -> $date")
            viewModel.isDateSelected = selected
            viewModel.startDateTimestamp = convertDateToTimestamp(
                _year = date.year,
                _month = date.month,
                _day = date.day
            )
            viewModel.endDateTimestamp = convertDateToTimestamp(
                _year = date.year,
                _month = date.month,
                _day = date.day
            )

            viewModel.checkAllSelected()
        }
    }
}