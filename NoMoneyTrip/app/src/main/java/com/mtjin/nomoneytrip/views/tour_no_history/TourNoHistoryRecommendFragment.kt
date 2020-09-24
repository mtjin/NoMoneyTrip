package com.mtjin.nomoneytrip.views.tour_no_history

import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.databinding.FragmentNoHistoryRecommendBinding
import com.mtjin.nomoneytrip.views.home.ProductHashTagAdapter

class TourNoHistoryRecommendFragment(
    private val product: Product,
    private val onClick: (Product) -> Unit
) :
    BaseFragment<FragmentNoHistoryRecommendBinding>(R.layout.fragment_no_history_recommend) {
    override fun init() {
        initAdapter()
        binding.item = product
        binding.root.setOnClickListener {
            onClick(product)
        }
    }

    private fun initAdapter() {
        val hashTagAdapter = ProductHashTagAdapter()
        binding.rvHashTags.adapter = hashTagAdapter
    }
}