package com.mtjin.nomoneytrip.views

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mtjin.nomoneytrip.R

@BindingAdapter("urlImage")
fun ImageView.setUrlImage(url: String) {
    Glide.with(this).load(url)
        .thumbnail(0.3f)
        .placeholder(R.drawable.img_product)
        .into(this)
}