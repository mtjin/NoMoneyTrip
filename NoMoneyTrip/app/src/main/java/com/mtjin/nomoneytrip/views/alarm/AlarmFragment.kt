package com.mtjin.nomoneytrip.views.alarm

import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentAlarmBinding
import com.mtjin.nomoneytrip.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlarmFragment : BaseFragment<FragmentAlarmBinding>(R.layout.fragment_alarm) {
    private val viewModel: AlarmViewModel by viewModel()

    override fun init() {
        binding.vm = viewModel
        initAdapter()
        initViewModelCallback()
        viewModel.requestNotifications()
    }

    private fun initAdapter() {
        val alarmAdapter = AlarmAdapter(onItemClick = { alarm ->
            if (alarm.readState) { //이미읽은알람
                when (alarm.case) {
                    ALARM_RESERVATION_COMPLETE_CASE1, ALARM_RESERVATION_ACCEPT_CASE2, ALARM_START_CASE3, ALARM_RESERVATION_REQUEST_CASE5,
                    ALARM_RESERVATION_DENY_CASE6 -> { //예약상세정보로 가야되는 로직
                        viewModel.goReservationDetail(alarm = alarm)
                    }
                    ALARM_REVIEW_CASE4 -> {//여행끝 리뷰알림, 리뷰로 가는 로직
                        viewModel.goReview()
                    }
                }
            } else { //아직 안읽은 알람
                when (alarm.case) {
                    ALARM_RESERVATION_COMPLETE_CASE1, ALARM_RESERVATION_ACCEPT_CASE2, ALARM_START_CASE3, ALARM_RESERVATION_REQUEST_CASE5,
                    ALARM_RESERVATION_DENY_CASE6, ALARM_REVIEW_CASE4 -> {
                        viewModel.updateAlarmReadState(alarm = alarm)
                    }
                }
            }
        })
        binding.rvAlarms.adapter = alarmAdapter
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            backClick.observe(this@AlarmFragment, Observer {
                findNavController().popBackStack()
            })
            requestNotificationsResult.observe(this@AlarmFragment, Observer {
                binding.clConstraintBasicAlarm.visibility = View.VISIBLE
            })

            goReservationDetail.observe(
                this@AlarmFragment,
                Observer { reservationProduct ->//예약상세정보로 가야되는 로직
                    findNavController().navigate(
                        AlarmFragmentDirections.actionAlarmFragmentToReservationDetailFragment(
                            reservationProduct
                        )
                    )
                })
            goTourHistory.observe(this@AlarmFragment, Observer {//여행끝 리뷰알림, 리뷰로 가는 로직
                findNavController().navigate(
                    AlarmFragmentDirections.actionAlarmFragmentToTourHistoryFragment(it.toTypedArray())
                )
            })

            goTourNoHistory.observe(this@AlarmFragment, Observer {//여행끝 리뷰알림, 리뷰로 가는 로직
                findNavController().navigate(AlarmFragmentDirections.actionAlarmFragmentToTourNoHistoryFragment())
            })
        }
    }

}