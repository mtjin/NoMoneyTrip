package com.mtjin.nomoneytrip.views.recommend_review

import androidx.navigation.fragment.findNavController
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentRecommendReviewBinding
import com.mtjin.nomoneytrip.views.community.CommunityAdapter
import com.mtjin.nomoneytrip.views.profile.ProfileFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecommendReviewFragment :
    BaseFragment<FragmentRecommendReviewBinding>(R.layout.fragment_recommend_review) {
    private val viewModel: RecommendReviewViewModel by viewModel()
    override fun init() {
        binding.vm = viewModel
        initAdapter()
        requestReviews()
    }

    private fun initAdapter() {
        val adapter = CommunityAdapter(context = thisContext, recommendClick = {
            viewModel.updateReviewRecommend(it)
        }, productClick = {
            findNavController().navigate(
                ProfileFragmentDirections.actionBottomNav4ToLodgmentDetailFragment(
                    it.product
                )
            )
        })
        binding.rvProducts.adapter = adapter
    }

    private fun requestReviews() {
        viewModel.requestMyRecommendReviews()
    }
}