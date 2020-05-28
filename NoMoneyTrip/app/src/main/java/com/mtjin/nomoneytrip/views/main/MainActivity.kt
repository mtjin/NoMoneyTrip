package com.mtjin.nomoneytrip.views.main

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseActivity
import com.mtjin.nomoneytrip.databinding.ActivityMainBinding
import com.skt.Tmap.TMapView


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
//        val tmapview = TMapView(this)
//        tmapview.setSKTMapApiKey(getString(R.string.tmap_key))
//
//        val linearLayoutTmap = findViewById<LinearLayout>(R.id.linearLayoutTmap)
//        val tMapView = TMapView(this)
//
//        tMapView.setSKTMapApiKey(getString(R.string.tmap_key))
//        linearLayoutTmap.addView(tMapView)
    }

    private fun initNavigation() {
        val navController = findNavController(R.id.main_nav_host)
        binding.mainBottomNavigation.setupWithNavController(navController)
    }
}
