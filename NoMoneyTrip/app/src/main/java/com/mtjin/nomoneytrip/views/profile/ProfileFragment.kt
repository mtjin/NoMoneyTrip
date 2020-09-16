package com.mtjin.nomoneytrip.views.profile

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentProfileBinding
import com.mtjin.nomoneytrip.utils.getMyColor
import com.mtjin.nomoneytrip.utils.getMyDrawable
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
        viewModel.requestMyReviews()
    }

    private fun initAdapter() {
        val productAdapter = CommunityAdapter(context = thisContext, recommendClick = {
            viewModel.updateReviewRecommend(it)
        }, productClick = {
            findNavController().navigate(
                ProfileFragmentDirections.actionBottomNav4ToLodgmentDetailFragment(
                    it.product
                )
            )
        })
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

            clickMyTour.observe(this@ProfileFragment, Observer {
                binding.tvMytour.setTextColor(thisContext.getMyColor(R.color.colorBlack2D2D))
                binding.ivHeart.setImageDrawable(thisContext.getMyDrawable(R.drawable.ic_community_good_off))
            })

            clickHeart.observe(this@ProfileFragment, Observer {
                binding.tvMytour.setTextColor(thisContext.getMyColor(R.color.colorGrayC8C8))
                binding.ivHeart.setImageDrawable(thisContext.getMyDrawable(R.drawable.ic_community_good))
            })
        }
    }

}