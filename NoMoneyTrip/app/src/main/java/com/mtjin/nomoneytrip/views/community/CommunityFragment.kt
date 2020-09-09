package com.mtjin.nomoneytrip.views.community

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentCommunityBinding

class CommunityFragment : BaseFragment<FragmentCommunityBinding>(R.layout.fragment_community) {
    private val viewModel: CommunityViewModel by viewModels()
    override fun init() {
        binding.vm = viewModel
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            goTourHistory.observe(this@CommunityFragment, Observer {
                findNavController().navigate(
                    CommunityFragmentDirections.actionBottomNav2ToTourHistoryFragment(it.toTypedArray())
                )
            })

            goTourNoHistory.observe(this@CommunityFragment, Observer {
                findNavController().navigate(CommunityFragmentDirections.actionBottomNav2ToTourNoHistoryFragment())
            })
        }
    }

}