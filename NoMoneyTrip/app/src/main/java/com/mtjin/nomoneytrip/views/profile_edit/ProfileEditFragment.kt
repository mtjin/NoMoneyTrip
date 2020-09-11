package com.mtjin.nomoneytrip.views.profile_edit

import androidx.navigation.fragment.navArgs
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentProfileEditBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileEditFragment :
    BaseFragment<FragmentProfileEditBinding>(R.layout.fragment_profile_edit) {
    private val args: ProfileEditFragmentArgs by navArgs()
    private val viewModel: ProfileEditViewModel by viewModel()
    override fun init() {
        binding.vm = viewModel
        processIntent()
    }

    private fun processIntent() {
        viewModel.setUser(args.user)
        viewModel.name.value = args.user.name
    }
}