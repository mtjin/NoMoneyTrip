package com.mtjin.nomoneytrip

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.skt.Tmap.TMapView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tmapview = TMapView(this)
        tmapview.setSKTMapApiKey(getString(R.string.tmap_key))

        val linearLayoutTmap = findViewById<LinearLayout>(R.id.linearLayoutTmap)
        val tMapView = TMapView(this)

        tMapView.setSKTMapApiKey(getString(R.string.tmap_key))
        linearLayoutTmap.addView(tMapView)
    }
}
