package com.mtjin.nomoneytrip.views.lodgment_detail

import androidx.navigation.fragment.navArgs
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentLodgementDetailBinding
import com.mtjin.nomoneytrip.views.home.ProductHashTagAdapter
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.fragment_lodgement_detail.*

class LodgmentDetailFragment :
    BaseFragment<FragmentLodgementDetailBinding>(R.layout.fragment_lodgement_detail) {
    private val productArg: LodgmentDetailFragmentArgs by navArgs()

    override fun init() {
        initAdapter()
        processIntent()
        initViewPager()
        initTmap()
    }

    private fun initViewPager() {
        binding.vpViewpager.adapter = ProductPagerAdapter(thisContext, productArg.product.imageList)
    }

    private fun initAdapter() {
        binding.rvHashTags.adapter = ProductHashTagAdapter()
    }

    private fun processIntent() {
        binding.product = productArg.product
    }

    private fun initTmap() {
        val tmapview = TMapView(context)
        tmapview.setSKTMapApiKey(getString(R.string.tmap_key))
        val tMapView = TMapView(context)
        tMapView.setSKTMapApiKey(getString(R.string.tmap_key))
        tmap.addView(tMapView)
    }
}