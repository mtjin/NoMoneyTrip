package com.mtjin.nomoneytrip.views.setting

import android.content.Intent
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentSettingBinding
import com.mtjin.nomoneytrip.views.login.LoginActivity
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
            goLogout.observe(this@SettingFragment, Observer {
                FirebaseAuth.getInstance().signOut()
                showToast(getString(R.string.logout_text))
                requireActivity().startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            })
            onInfoClick.observe(this@SettingFragment, Observer {
                showToast("준비중")
            })
            onRuleClick.observe(this@SettingFragment, Observer {
                showToast("준비중")
            })
            onPersonalInfoClick.observe(this@SettingFragment, Observer {
                showToast("준비중")
            })
            onLocationInfoClick.observe(this@SettingFragment, Observer {
                showToast("준비중")
            })
            onDeleteAuthClick.observe(this@SettingFragment, Observer {
                showToast("준비중")
            })
        }

    }
}