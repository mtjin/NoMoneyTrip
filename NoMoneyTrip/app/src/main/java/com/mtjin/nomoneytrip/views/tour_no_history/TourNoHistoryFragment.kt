package com.mtjin.nomoneytrip.views.tour_no_history

import androidx.lifecycle.Observer
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentTourNoHistoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TourNoHistoryFragment :
    BaseFragment<FragmentTourNoHistoryBinding>(R.layout.fragment_tour_no_history) {
    private val viewModel: TourNoHistoryViewModel by viewModel()
    lateinit var pagerAdapter: TourNoHistoryPagerAdapter

    override fun init() {
        initViewPager()
        initViewModelCallback()
        viewModel.requestProducts()
    }

    private fun initViewPager() {
        binding.vpViewpager.offscreenPageLimit = 5 //캐시해놓을 페이지 수
        pagerAdapter = TourNoHistoryPagerAdapter(requireActivity().supportFragmentManager)
        binding.vpViewpager.adapter = pagerAdapter
        val dpValue = 54
        val d = resources.displayMetrics.density
        val margin = (dpValue * d).toInt()
        binding.vpViewpager.clipToPadding = false
        binding.vpViewpager.setPadding(margin, 0, margin, 0)
        binding.vpViewpager.pageMargin = margin / 2
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            productList.observe(this@TourNoHistoryFragment, Observer { products ->
                val list = ArrayList<TourNoHistoryRecommendFragment>()
                for (product in products) {
                    list.add(TourNoHistoryRecommendFragment(product))
                }
                binding.pivPageIndicator.count = products.size
                pagerAdapter.addItems(list)
            })
        }
    }
}