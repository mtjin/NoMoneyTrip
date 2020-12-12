package com.mtjin.nomoneytrip.views.profile

import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.data.master_write.MasterLetter
import com.mtjin.nomoneytrip.databinding.FragmentProfileBinding
import com.mtjin.nomoneytrip.views.community.CommunityAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val viewModel: ProfileViewModel by viewModel()
    lateinit var letterAdapter: ProfileMasterLetterAdapter
    override fun init() {
        binding.vm = viewModel
        initAdapter()
        initViewModelCallback()
        requestItems()
    }

    private fun requestItems() {
        viewModel.requestProfile()
        viewModel.requestMyReviews()
        viewModel.requestMasterLetters()
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
        letterAdapter = ProfileMasterLetterAdapter()

        binding.rvTours.adapter = tourDiaryAdapter
        binding.rvLetters.adapter = letterAdapter
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
                findNavController().navigate(
                    ProfileFragmentDirections.actionBottomNav4ToRecommendReviewFragment()
                )
            })

            userReviewList.observe(this@ProfileFragment, Observer {
                binding.run {
                    if (it.isEmpty()) {
                        val height = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            180f,
                            resources.displayMetrics
                        ).toInt()
                        rvTours.layoutParams.height = height
                        ivNoMyTour.visibility = View.VISIBLE
                        rvTours.visibility = View.INVISIBLE
                    } else {
                        ivNoMyTour.visibility = View.GONE
                        rvTours.visibility = View.VISIBLE
                        rvTours.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                }
            })

            masterLetterList.observe(this@ProfileFragment, Observer {
                letterAdapter.run {
                    clear()
                    addItems(it as List<MasterLetter>)
                }
            })
        }
    }

}