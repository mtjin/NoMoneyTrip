package com.mtjin.nomoneytrip.views.tour_no_history

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentTourNoHistoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TourNoHistoryFragment :
    BaseFragment<FragmentTourNoHistoryBinding>(R.layout.fragment_tour_no_history) {
    private val viewModel: TourNoHistoryViewModel by viewModel()
    private lateinit var pagerAdapter: TourNoHistoryPagerAdapter

    override fun init() {
        binding.vm = viewModel
        initViewModelCallback()
        initViewPager()
        viewModel.requestProducts()
    }

    private fun initViewPager() {
        //binding.vpViewpager.offscreenPageLimit = 5 //캐시해놓을 페이지 수
        pagerAdapter = TourNoHistoryPagerAdapter(requireActivity().supportFragmentManager)
        binding.vpViewpager.run {
            adapter = pagerAdapter
            val dpValue = 54
            val d = resources.displayMetrics.density
            val margin = (dpValue * d).toInt()
            clipToPadding = false
            setPadding(margin, 0, margin, 0)
            pageMargin = margin / 2
        }
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            productList.observe(this@TourNoHistoryFragment, Observer { products ->
                initViewPager()
                var size = 0
                for (i in 0..4) {
                    if (products.size <= i) {
                        break
                    }
                    size++
                    pagerAdapter.addItem(TourNoHistoryRecommendFragment(product = products[i], onClick = {
                        findNavController().navigate(
                            TourNoHistoryFragmentDirections.actionTourNoHistoryFragmentToLodgmentDetailFragment(
                                it
                            )
                        )
                    }))
                }
            })

            goHome.observe(this@TourNoHistoryFragment, Observer {
                findNavController().navigate(TourNoHistoryFragmentDirections.actionTourNoHistoryFragmentToBottomNav1())
            })

            backClick.observe(this@TourNoHistoryFragment, Observer {
                findNavController().popBackStack()
            })
        }
    }
}