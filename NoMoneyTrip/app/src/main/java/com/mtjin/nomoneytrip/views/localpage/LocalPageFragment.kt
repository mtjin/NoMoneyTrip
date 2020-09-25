package com.mtjin.nomoneytrip.views.localpage

import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.AppBarLayout
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentLocalPageBinding
import com.mtjin.nomoneytrip.utils.TAG
import com.mtjin.nomoneytrip.views.community.CommunityAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class LocalPageFragment : BaseFragment<FragmentLocalPageBinding>(R.layout.fragment_local_page) {
    private val safeArgs: LocalPageFragmentArgs by navArgs()
    private val viewModel: LocalPageViewModel by viewModel()
    private lateinit var tourIntroduceAdapter: LocalPageAdapter
    private lateinit var restaurantIntroduceAdapter: LocalPageAdapter
    private lateinit var productAdapter: LocalProductAdapter
    private lateinit var reviewAdapter: CommunityAdapter

    override fun init() {
        binding.vm = viewModel
        viewModel.page = 2
        initView()
        initAdapter()
        processIntent()
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            backClick.observe(this@LocalPageFragment, Observer {
                findNavController().popBackStack()
            })

            goLodgeSearch.observe(this@LocalPageFragment, Observer {
                findNavController().navigate(
                    LocalPageFragmentDirections.actionLocalpageFragmentToSearchFragment()
                )
            })
        }
    }

    private fun initView() {
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) { // 접혔을때
                binding.tvLocalToolbarTitle.visibility = View.VISIBLE
                binding.viewToolbarLine.visibility = View.VISIBLE
            } else {// 펴졌을때
                binding.tvLocalToolbarTitle.visibility = View.GONE
                binding.viewToolbarLine.visibility = View.GONE
            }
        })
    }

    private fun initAdapter() {
        tourIntroduceAdapter = LocalPageAdapter {
            findNavController().navigate(
                LocalPageFragmentDirections.actionLocalpageFragmentToWebViewFragment(
                    "http://api.visitkorea.or.kr/guide/tourDetail.do?contentId=" + it.contentid + "&langtype=KOR&oper=area&burl=&contentTypeId=" + it.contenttypeid + "&areaCode=&sigunguCode=&cat1=&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A"
                )
            )
        }
        restaurantIntroduceAdapter = LocalPageAdapter {
            findNavController().navigate(
                LocalPageFragmentDirections.actionLocalpageFragmentToWebViewFragment(
                    "http://api.visitkorea.or.kr/guide/tourDetail.do?contentId=" + it.contentid + "&langtype=KOR&oper=area&burl=&contentTypeId=" + it.contenttypeid + "&areaCode=&sigunguCode=&cat1=&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A"
                )
            )
        }
        productAdapter = LocalProductAdapter(context = thisContext, itemClick = {
            findNavController().navigate(
                LocalPageFragmentDirections.actionLocalpageFragmentToLodgmentDetailFragment(
                    it
                )
            )
        }, favoriteClick = {
            viewModel.updateProductFavorite(it)
        })
        reviewAdapter = CommunityAdapter(context = thisContext, recommendClick = {
            viewModel.updateReviewRecommend(it)
        }, productClick = {
            findNavController().navigate(
                LocalPageFragmentDirections.actionLocalpageFragmentToLodgmentDetailFragment(
                    it.product
                )
            )
        })
        binding.rvTours.adapter = tourIntroduceAdapter
        binding.rvRestaurants.adapter = restaurantIntroduceAdapter
        binding.rvProducts.adapter = productAdapter
        binding.rvReviews.adapter = reviewAdapter

    }

    private fun processIntent() {
        Log.d(TAG, "LocalPageFragment safeArgs -> " + safeArgs.local)
        val local = ""
        viewModel.city = local
        viewModel.requestReviews()
        viewModel.requestProducts()
        binding.tvLocalTitle.text = local
        binding.tvLocalToolbarTitle.text = local
    }
}