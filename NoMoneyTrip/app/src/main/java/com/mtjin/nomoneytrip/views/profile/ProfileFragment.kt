package com.mtjin.nomoneytrip.views.profile

import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentProfileBinding
import com.mtjin.nomoneytrip.utils.getMyColor
import com.mtjin.nomoneytrip.utils.getMyDrawable
import com.mtjin.nomoneytrip.views.community.CommunityAdapter
import com.mtjin.nomoneytrip.views.localpage.LocalPageFragmentDirections
import com.mtjin.nomoneytrip.views.localpage.LocalProductAdapter
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
        val tourDiaryAdapter = CommunityAdapter(context = thisContext, recommendClick = {
            viewModel.updateReviewRecommend(it)
        }, productClick = {
            findNavController().navigate(
                ProfileFragmentDirections.actionBottomNav4ToLodgmentDetailFragment(
                    it.product
                )
            )
        })

        binding.rvTours.adapter = tourDiaryAdapter
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

            goSetting.observe(this@ProfileFragment, Observer {
                findNavController().navigate(
                    ProfileFragmentDirections.actionBottomNav4ToSettingFragment()
                )
            })

            clickHeart.observe(this@ProfileFragment, Observer {

            })
        }
    }

}