package com.mtjin.nomoneytrip.views.localpage

import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.AppBarLayout
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentLocalPageBinding
import com.mtjin.nomoneytrip.utils.*
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
                binding.ivBack.setImageDrawable(requireActivity().getMyDrawable(R.drawable.ic_back_title))
                binding.ivSearch.setImageDrawable(requireActivity().getMyDrawable(R.drawable.ic_search_black))
            } else {// 펴졌을때
                binding.tvLocalToolbarTitle.visibility = View.GONE
                binding.viewToolbarLine.visibility = View.GONE
                binding.ivBack.setImageDrawable(requireActivity().getMyDrawable(R.drawable.ic_back_white))
                binding.ivSearch.setImageDrawable(requireActivity().getMyDrawable(R.drawable.ic_search_white))
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
        var local = ""
        when (safeArgs.local) {
            "seoul" -> {
                local = "서울"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.seoul2
                    )
                )
                viewModel.requestTourIntroduces(SEOUL_CODE)
                viewModel.requestRestaurantIntroduces(SEOUL_CODE)
            }
            "incheon" -> {
                local = "인천"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.incheon
                    )
                )
                viewModel.requestTourIntroduces(INCHEON_CODE)
                viewModel.requestRestaurantIntroduces(INCHEON_CODE)
            }
            "daejeon" -> {
                local = "대전"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.daejeon
                    )
                )
                viewModel.requestTourIntroduces(DAEJEON_CODE)
                viewModel.requestRestaurantIntroduces(DAEJEON_CODE)
            }
            "daegu" -> {
                local = "대구"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.daegu
                    )
                )
                viewModel.requestTourIntroduces(DAEGU_CODE)
                viewModel.requestRestaurantIntroduces(DAEGU_CODE)
            }
            "gwangju" -> {
                local = "광주"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.gwangju
                    )
                )
                viewModel.requestTourIntroduces(GWANGJU_CODE)
                viewModel.requestRestaurantIntroduces(GWANGJU_CODE)
            }
            "busan" -> {
                local = "부산"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.busan
                    )
                )
                viewModel.requestTourIntroduces(BUSAN_CODE)
                viewModel.requestRestaurantIntroduces(BUSAN_CODE)
            }
            "ulsan" -> {
                local = "울산"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.ulsan
                    )
                )
                viewModel.requestTourIntroduces(ULSAN_CODE)
                viewModel.requestRestaurantIntroduces(ULSAN_CODE)
            }
            "sejong" -> {
                local = "세종"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.sejong
                    )
                )
                viewModel.requestTourIntroduces(SEJONG_CODE)
                viewModel.requestRestaurantIntroduces(SEJONG_CODE)
            }
            "gyeungi" -> {
                local = "경기"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.gyeungi
                    )
                )
                viewModel.requestTourIntroduces(GYEUNGI_CODE)
                viewModel.requestRestaurantIntroduces(GYEUNGI_CODE)
            }
            "kangwon" -> {
                local = "강원"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.kangwon
                    )
                )
                viewModel.requestTourIntroduces(KANGWON_CODE)
                viewModel.requestRestaurantIntroduces(KANGWON_CODE)
            }
            "choongbuk" -> {
                local = "충북"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.choongbuk
                    )
                )
                viewModel.requestTourIntroduces(CHOONGBUK_CODE)
                viewModel.requestRestaurantIntroduces(CHOONGBUK_CODE)
            }
            "choongnam" -> {
                local = "충남"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.choongnam
                    )
                )
                viewModel.requestTourIntroduces(CHOONGNAM_CODE)
                viewModel.requestRestaurantIntroduces(CHOONGNAM_CODE)
            }
            "gyungbuk" -> {
                local = "경북"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.gyungbuk
                    )
                )
                viewModel.requestTourIntroduces(GYUNGBUK_CODE)
                viewModel.requestRestaurantIntroduces(GYUNGBUK_CODE)
            }
            "gyungnam" -> {
                local = "경남"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.gyungnam
                    )
                )
                viewModel.requestTourIntroduces(GYUNGNAM_CODE)
                viewModel.requestRestaurantIntroduces(GYUNGNAM_CODE)
            }
            "jeonbuk" -> {
                local = "전북"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.jeonbuk
                    )
                )
                viewModel.requestTourIntroduces(JEONBUK_CODE)
                viewModel.requestRestaurantIntroduces(JEONBUK_CODE)
            }
            "jeonnam" -> {
                local = "전남"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.jeonnam
                    )
                )
                viewModel.requestTourIntroduces(JEONNAM_CODE)
                viewModel.requestRestaurantIntroduces(JEONNAM_CODE)
            }
            "jeju" -> {
                local = "제주"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.jeju
                    )
                )
                viewModel.requestTourIntroduces(JEJU_CODE)
                viewModel.requestRestaurantIntroduces(JEJU_CODE)
            }
        }
        viewModel.city = local
        viewModel.requestReviews()
        viewModel.requestProducts()
        binding.tvLocalTitle.text = local
        binding.tvLocalToolbarTitle.text = local
    }
}