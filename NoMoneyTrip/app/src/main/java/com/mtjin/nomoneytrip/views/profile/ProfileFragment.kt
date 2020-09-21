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

        val productAdapter = LocalProductAdapter(context = thisContext, itemClick = {
            findNavController().navigate(
                LocalPageFragmentDirections.actionLocalpageFragmentToLodgmentDetailFragment(
                    it
                )
            )
        }, favoriteClick = {
            viewModel.updateProductFavorite(it)
        })

        binding.rvTours.adapter = tourDiaryAdapter
        binding.rvProducts.adapter = productAdapter
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

            clickMyTour.observe(this@ProfileFragment, Observer {
                binding.rvProducts.visibility = View.GONE
                binding.rvTours.visibility = View.VISIBLE
                binding.tvThanksLetter.visibility = View.VISIBLE
                binding.tvMytour.setTextColor(thisContext.getMyColor(R.color.colorBlack2D2D))
                binding.ivHeart.setImageDrawable(thisContext.getMyDrawable(R.drawable.ic_community_good_off))
                binding.ivFavorite.setImageDrawable(thisContext.getMyDrawable(R.drawable.ic_save_black_off))
            })

            clickHeart.observe(this@ProfileFragment, Observer {
                binding.rvProducts.visibility = View.GONE
                binding.rvTours.visibility = View.VISIBLE
                binding.tvThanksLetter.visibility = View.VISIBLE
                binding.tvMytour.setTextColor(thisContext.getMyColor(R.color.colorGrayC8C8))
                binding.ivHeart.setImageDrawable(thisContext.getMyDrawable(R.drawable.ic_community_good))
                binding.ivFavorite.setImageDrawable(thisContext.getMyDrawable(R.drawable.ic_save_black_off))
            })

            clickFavorite.observe(this@ProfileFragment, Observer {
                binding.rvTours.visibility = View.GONE
                binding.rvProducts.visibility = View.VISIBLE
                binding.tvThanksLetter.visibility = View.GONE
                binding.tvMytour.setTextColor(thisContext.getMyColor(R.color.colorGrayC8C8))
                binding.ivHeart.setImageDrawable(thisContext.getMyDrawable(R.drawable.ic_community_good_off))
                binding.ivFavorite.setImageDrawable(thisContext.getMyDrawable(R.drawable.ic_save_on))
            })
        }
    }

}