package com.mtjin.nomoneytrip.views.tour_detail

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.data.local_page.TourIntroduce
import com.mtjin.nomoneytrip.databinding.FragmentTourDetailBinding
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapTapi
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.fragment_lodgement_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TourDetailFragment : BaseFragment<FragmentTourDetailBinding>(R.layout.fragment_tour_detail) {
    private val viewModel: TourDetailViewModel by viewModel()
    private val args: TourDetailFragmentArgs by navArgs()
    private lateinit var tourIntroduce: TourIntroduce

    override fun init() {
        binding.vm = viewModel
        processIntent()
        initTmap()
        initViewModelCallback()
    }

    private fun initTmap() {
        //지도 위치설정
        val xPos = tourIntroduce.mapx.toString().toDouble() //좌표
        val yPos = tourIntroduce.mapy.toString().toDouble()
        val tmapView = TMapView(context)
        tmapView.setSKTMapApiKey(getString(R.string.tmap_key))
        tmapView.setCenterPoint(
            xPos,
            yPos
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
            TMapPoint(yPos, xPos)
        val markerItem1 = TMapMarkerItem()
        markerItem1.icon = bitmap // 마커 아이콘 지정
        markerItem1.tMapPoint = tMapPoint1 // 마커의 좌표 지정
        markerItem1.name = getString(R.string.no_money_diary_text) // 마커의 타이틀 지정
        tmapView.addMarkerItem("markerItem1", markerItem1) // 지도에 마커 추가
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            requestSuccessMsg.observe(this@TourDetailFragment, Observer { success ->
                if (!success) showToast("통신 오류")
            })

            searchDirection.observe(this@TourDetailFragment, Observer {
                val tMapTapi = TMapTapi(thisContext)
                if (tMapTapi.isTmapApplicationInstalled) tMapTapi.invokeRoute(
                    tourIntroduce.addr1,
                    tourIntroduce.mapx.toString().toFloat(), //좌표
                    tourIntroduce.mapy.toString().toFloat()
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

            tourProductDetail.observe(this@TourDetailFragment, Observer {
                if (it?.homepage.isNullOrBlank()) {
                    binding.textHomepage.visibility = View.GONE
                    binding.tvHomepage.visibility = View.GONE
                } else {
                    binding.textHomepage.visibility = View.VISIBLE
                    binding.tvHomepage.visibility = View.VISIBLE
                }
            })

            share.observe(this@TourDetailFragment, Observer {
                Toast.makeText(context, getString(R.string.update_later_msg), Toast.LENGTH_SHORT)
                    .show()
            })

            backClick.observe(this@TourDetailFragment, Observer {
                findNavController().popBackStack()
            })
        }
    }

    private fun processIntent() {
        tourIntroduce = args.tourIntroduce
        binding.tourIntroduce = tourIntroduce
        viewModel.requestTourProductDetails(tourIntroduce)
    }
}