package com.mtjin.nomoneytrip.views.app_info

import android.content.pm.PackageInfo
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentAppInfoBinding

class AppInfoFragment : BaseFragment<FragmentAppInfoBinding>(R.layout.fragment_app_info) {
    override fun init() {
        getVersionInfo()
    }

    private fun getVersionInfo() {
        val info: PackageInfo =
            requireActivity().packageManager.getPackageInfo(requireActivity().packageName, 0)
        val version = info.versionName
        binding.tvAppVersion.text = version.toString()
    }
}