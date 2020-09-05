package com.mtjin.nomoneytrip.views.tour_no_history

import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentTourNoHistoryBinding

class TourNoHistoryFragment :
    BaseFragment<FragmentTourNoHistoryBinding>(R.layout.fragment_tour_no_history) {
    override fun init() {
        binding.vpViewpager.offscreenPageLimit = 3 //캐시해놓을 페이지 수
//        val adapter  = TourNoHistoryPagerAdapter(items)
//        binding.vpViewpager.adapter = adapter
    }
}