package com.mtjin.nomoneytrip.views.alarm

import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentAlarmBinding
import com.mtjin.nomoneytrip.views.community.CommunityAdapter
import com.mtjin.nomoneytrip.views.community.CommunityFragmentDirections
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
        val alarmAdapter = AlarmAdapter()
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
        }
    }

}