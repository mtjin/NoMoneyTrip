package com.mtjin.nomoneytrip.views.alarm

import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentAlarmBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlarmFragment : BaseFragment<FragmentAlarmBinding>(R.layout.fragment_alarm) {
    private val viewModel: AlarmViewModel by viewModel()

    override fun init() {
        binding.vm = viewModel
    }

}