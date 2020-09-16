package com.mtjin.nomoneytrip.views.community

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentCommunityBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommunityFragment : BaseFragment<FragmentCommunityBinding>(R.layout.fragment_community) {
    private val viewModel: CommunityViewModel by viewModel()
    override fun init() {
        binding.vm = viewModel
        initViewModelCallback()
        initAdapter()
        viewModel.requestReviews()
    }

    private fun initAdapter() {
        binding.rvReviews.adapter = CommunityAdapter(context = thisContext, recommendClick = {
            viewModel.updateReviewRecommend(it)
        }, productClick = {
            findNavController().navigate(
                CommunityFragmentDirections.actionBottomNav2ToLodgmentDetailFragment(
                    it.product
                )
            )
        })
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