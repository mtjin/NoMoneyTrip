package com.mtjin.nomoneytrip.views.lodgment_detail

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentLodgementDetailBinding
import com.mtjin.nomoneytrip.views.home.ProductHashTagAdapter
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.fragment_lodgement_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LodgmentDetailFragment :
    BaseFragment<FragmentLodgementDetailBinding>(R.layout.fragment_lodgement_detail) {
    private val productArg: LodgmentDetailFragmentArgs by navArgs()
    private val viewModel: LodgmentDetailViewModel by viewModel()

    override fun init() {
        binding.vm = viewModel
        initHashTagAdapter()
        processIntent()
        initViewPager()
        initReviewAdapter()
        initTmap()
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            goReservationFirst.observe(this@LodgmentDetailFragment, Observer {
                findNavController().navigate(
                    LodgmentDetailFragmentDirections.actionLodgmentDetailFragmentToReservationFirstFragment(
                        productArg.product
                    )
                )
            })
        }
    }

    private fun initReviewAdapter() {
        binding.rvReviews.adapter = ReviewAdapter()
        // TODO :: 아이템 추가작업 추후 구현
    }

    private fun initViewPager() {
        binding.vpViewpager.adapter = ProductPagerAdapter(thisContext, productArg.product.imageList)
        binding.tvPagerNum.text = ("" + 1 + " / " + productArg.product.imageList.size.toString())
        binding.vpViewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvPagerNum.text =
                    ((position + 1).toString() + " / " + productArg.product.imageList.size.toString())
            }
        })
    }

    private fun initHashTagAdapter() {
        binding.rvHashTags.adapter = ProductHashTagAdapter()
    }

    private fun processIntent() {
        binding.product = productArg.product
    }

    private fun initTmap() {
        val tmapView = TMapView(context)
        tmapView.setSKTMapApiKey(getString(R.string.tmap_key))
        val tMapView = TMapView(context)
        tMapView.setSKTMapApiKey(getString(R.string.tmap_key))
        tmap.addView(tMapView)
    }
}