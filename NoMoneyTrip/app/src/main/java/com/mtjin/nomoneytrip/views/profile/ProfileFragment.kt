package com.mtjin.nomoneytrip.views.profile

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentProfileBinding
import com.mtjin.nomoneytrip.views.community.CommunityAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val viewModel: ProfileViewModel by viewModel()
    override fun init() {
        binding.vm = viewModel
        initAdapter()
        initViewModelCallback()
        requestItems()
    }

    private fun requestItems() {
        viewModel.requestProfile()
        viewModel.requestReviews()
    }

    private fun initAdapter() {
        val productAdapter = CommunityAdapter(thisContext) {
            viewModel.updateReviewRecommend(it)
        }
        binding.rvTours.adapter = productAdapter
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            goProfileEdit.observe(this@ProfileFragment, Observer {
                viewModel.user.value?.let {
                    findNavController().navigate(
                        ProfileFragmentDirections.actionBottomNav4ToProfileEditFragment(it)
                    )
                }
            })
        }
    }

}