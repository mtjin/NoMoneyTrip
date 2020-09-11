package com.mtjin.nomoneytrip.views.lodgment_detail

import android.graphics.BitmapFactory
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentLodgementDetailBinding
import com.mtjin.nomoneytrip.views.home.ProductHashTagAdapter
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapTapi
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
            searchDirection.observe(this@LodgmentDetailFragment, Observer {
                val tMapTapi = TMapTapi(thisContext)
                if (tMapTapi.isTmapApplicationInstalled) tMapTapi.invokeRoute(
                    productArg.product.address,
                    productArg.product.yPos.toFloat(),
                    productArg.product.xPos.toFloat()
                )
                else showToast(getString(R.string.please_install_tmap_msg))
            })
            backClick.observe(this@LodgmentDetailFragment, Observer {
                findNavController().popBackStack()
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
        //지도 위치설정
        val xPos = productArg.product.xPos.toDouble() //좌표
        val yPos = productArg.product.yPos.toDouble()
        val tmapView = TMapView(context)
        tmapView.setSKTMapApiKey(getString(R.string.tmap_key))
        tmapView.setCenterPoint(
            yPos,
            xPos
        )
        tmapView.zoomLevel = 20 // 클수록 더 줌된 상태
        tmap.addView(tmapView) //tmap(xml레이아웃)에 tmapView 동적 추가
        //마커추가
        val bitmap =
            BitmapFactory.decodeResource(
                requireActivity().resources,
                R.drawable.ic_location_orange_12
            )
        val tMapPoint1 =
            TMapPoint(xPos, yPos)
        val markerItem1 = TMapMarkerItem()
        markerItem1.icon = bitmap // 마커 아이콘 지정
        markerItem1.tMapPoint = tMapPoint1 // 마커의 좌표 지정
        markerItem1.name = getString(R.string.no_money_diary_text) // 마커의 타이틀 지정
        tmapView.addMarkerItem("markerItem1", markerItem1) // 지도에 마커 추가
    }
}