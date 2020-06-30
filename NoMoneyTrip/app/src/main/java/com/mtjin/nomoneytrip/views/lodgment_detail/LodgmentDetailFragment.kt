package com.mtjin.nomoneytrip.views.lodgment_detail

import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentLodgementDetailBinding
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.fragment_lodgement_detail.*

class LodgmentDetailFragment :
    BaseFragment<FragmentLodgementDetailBinding>(R.layout.fragment_lodgement_detail) {
    override fun init() {
        val tmapview = TMapView(context)
        tmapview.setSKTMapApiKey(getString(R.string.tmap_key))
        val tMapView = TMapView(context)
        tMapView.setSKTMapApiKey(getString(R.string.tmap_key))
        tmap.addView(tMapView)
    }
}