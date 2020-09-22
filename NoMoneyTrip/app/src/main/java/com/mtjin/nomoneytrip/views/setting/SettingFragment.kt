package com.mtjin.nomoneytrip.views.setting

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentSettingBinding
import com.mtjin.nomoneytrip.utils.APP_LOCATION_INFO_RULE
import com.mtjin.nomoneytrip.utils.APP_PERSONAL_INFO_RULE
import com.mtjin.nomoneytrip.utils.APP_RULE_URL
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
            onInfoClick.observe(this@SettingFragment, Observer {//앱정보
                findNavController().navigate(
                    SettingFragmentDirections.actionSettingFragmentToAppInfoFragment()
                )
            })
            onRuleClick.observe(this@SettingFragment, Observer { //이용약관
                findNavController().navigate(
                    SettingFragmentDirections.actionSettingFragmentToWebViewFragment(APP_RULE_URL)
                )
            })
            onPersonalInfoClick.observe(this@SettingFragment, Observer { //개인정보처리방침
                findNavController().navigate(
                    SettingFragmentDirections.actionSettingFragmentToWebViewFragment(
                        APP_PERSONAL_INFO_RULE
                    )
                )
            })
            onLocationInfoClick.observe(this@SettingFragment, Observer { //위치정보이용약관
                findNavController().navigate(
                    SettingFragmentDirections.actionSettingFragmentToWebViewFragment(
                        APP_LOCATION_INFO_RULE
                    )
                )
            })
            onDeleteAuthClick.observe(this@SettingFragment, Observer { //회원탈퇴
                showToast("준비중")
            })
        }

    }
}