package com.mtjin.nomoneytrip.views.lodgment_detail

import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentLodgementDetailBinding
import com.mtjin.nomoneytrip.utils.extensions.getMyDrawable
import com.mtjin.nomoneytrip.utils.uuid
import com.mtjin.nomoneytrip.views.community.CommunityAdapter
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
        initView()
        initReviewAdapter()
        initTmap()
        initViewModelCallback()
    }

    private fun initView() {
        productArg.product.run {
            if (favoriteList.contains(uuid)) binding.ivFavorite.setImageDrawable(
                thisContext.getMyDrawable(R.drawable.ic_good_on)
            )
            else binding.ivFavorite.setImageDrawable(thisContext.getMyDrawable(R.drawable.ic_good_off_detail))
            // 편의시설
            if (!market) binding.ivMarket.alpha = 0.3f
            if (!internet) binding.ivInternet.alpha = 0.3f
            if (!parking) binding.ivParking.alpha = 0.3f
            if (!animal) binding.ivAnimal.alpha = 0.3f
        }

        binding.svScrollview.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (isVisible(binding.tvTopReservation)) binding.clBottomReservation.visibility =
                View.GONE
            else binding.clBottomReservation.visibility = View.VISIBLE
        })
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
                else {
                    showToast(getString(R.string.please_install_tmap_msg))
                    val result = tMapTapi.tMapDownUrl
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(result[0])
                        )
                    )
                }
            })

            updateFavoriteResult.observe(this@LodgmentDetailFragment, Observer { favoriteOn ->
                if (favoriteOn) binding.ivFavorite.setImageDrawable(
                    thisContext.getMyDrawable(R.drawable.ic_good_on)
                )
                else binding.ivFavorite.setImageDrawable(thisContext.getMyDrawable(R.drawable.ic_good_off_detail))
            })

            lastReviewCall.observe(this@LodgmentDetailFragment, Observer {
                binding.run {
                    if (!viewModel.getIsFragmentFromBackStack()) {
                        tvMore.visibility = View.GONE
                        showToast(getString(R.string.last_tour_history_msg))
                    } else {
                        viewModel.setIsFragmentFromBackStack(false)
                    }
                }
            })

            share.observe(this@LodgmentDetailFragment, Observer {
                Toast.makeText(context, getString(R.string.update_later_msg), Toast.LENGTH_SHORT)
                    .show()
            })

            backClick.observe(this@LodgmentDetailFragment, Observer {
                findNavController().popBackStack()
            })
        }
    }

    private fun initReviewAdapter() {
        viewModel.page = 2
        val adapter = CommunityAdapter(context = thisContext, recommendClick = {
            viewModel.updateReviewRecommend(it)
        }, productClick = {
        })
        binding.rvReviews.adapter = adapter
        viewModel.requestReviews()
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

    private fun isVisible(view: View?): Boolean {
        if (view == null) {
            return false
        }
        if (!view.isShown) {
            return false
        }
        val actualPosition = Rect()
        view.getGlobalVisibleRect(actualPosition)
        val screen = Rect(
            0,
            0,
            Resources.getSystem().displayMetrics.widthPixels,
            Resources.getSystem().displayMetrics.heightPixels
        )
        return actualPosition.intersect(screen)
    }

    private fun initHashTagAdapter() {
        binding.rvHashTags.adapter = ProductHashTagAdapter()
    }

    private fun processIntent() {
        productArg.product.let {
            binding.product = it
            viewModel.productId = it.id
            viewModel.product = it
        }
    }

    private fun initTmap() {
        //지도 위치설정
        val xPos = productArg.product.xPos.toDouble() //좌표
        val yPos = productArg.product.yPos.toDouble()
        val tmapView = TMapView(context)
        tmapView.apply {
            setSKTMapApiKey(getString(R.string.tmap_key))
            setCenterPoint(yPos, xPos)
            zoomLevel = 20 // 클수록 더 줌된 상태
        }
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
        markerItem1.apply {
            icon = bitmap // 마커 아이콘 지정
            tMapPoint = tMapPoint1 // 마커의 좌표 지정
            name = getString(R.string.no_money_diary_text) // 마커의 타이틀 지정
        }
        tmapView.addMarkerItem("markerItem1", markerItem1) // 지도에 마커 추가
    }
}