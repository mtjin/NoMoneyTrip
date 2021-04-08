package com.mtjin.nomoneytrip.views.lodgment_detail

import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.link.rx
import com.kakao.sdk.template.model.*
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.databinding.FragmentLodgementDetailBinding
import com.mtjin.nomoneytrip.utils.TAG
import com.mtjin.nomoneytrip.utils.extensions.getMyDrawable
import com.mtjin.nomoneytrip.utils.uuid
import com.mtjin.nomoneytrip.views.community.CommunityAdapter
import com.mtjin.nomoneytrip.views.home.ProductHashTagAdapter
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapTapi
import com.skt.Tmap.TMapView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
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
        }, shareClick = {
            sendKakaoLink(it)
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

    private fun sendKakaoLink(userReview: UserReview) {
        // 메시지 템플릿 만들기 (피드형)
        val defaultFeed = FeedTemplate(
            content = Content(
                title = userReview.product.title,
                description = userReview.review.content,
                imageUrl = userReview.review.image,
                link = Link(
                    mobileWebUrl = "https://play.google.com/store/apps/details?id=com.mtjin.nomoneytrip"
                )
            ), social = Social(
                likeCount = userReview.review.recommendList.size
            ),
            buttons = listOf(
                Button(
                    "앱으로 보기",
                    Link(
                        androidExecParams = mapOf(
                            "key1" to "value1",
                            "key2" to "value2"
                        ) // 내 앱에서 파라미터보낼건 필요없음 (앱 다운로드만 유도할것이다)
                    )
                )
            )
        )
        // 피드 메시지 보내기
        LinkClient.rx.defaultTemplate(thisContext, defaultFeed)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ linkResult ->
                Log.d(TAG, "카카오링크 보내기 성공 ${linkResult.intent}")
                startActivity(linkResult.intent)

                // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                Log.w(TAG, "Warning Msg: ${linkResult.warningMsg}")
                Log.w(TAG, "Argument Msg: ${linkResult.argumentMsg}")
            }, { error ->
                showToast(getString(R.string.kakao_link_fail_msg))
                Log.e(TAG, "카카오링크 보내기 실패 ", error)
            })
            .addTo(compositeDisposable)
    }
}