package com.mtjin.nomoneytrip.views.setting

import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentSettingBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {
    private val viewModel: SettingViewModel by viewModel()
    override fun init() {
        binding.vm = viewModel
        initView()
        initViewModelCallback()
    }

    private fun initView() {
        binding.swAlarm.isChecked = viewModel.getSettingAlarm()
        binding.swAlarm.setOnCheckedChangeListener { p0, isChecked ->
            viewModel.setSettingAlarm(isChecked)
        }
    }

    private fun initViewModelCallback() {
        with(viewModel) {


        }
    }
}