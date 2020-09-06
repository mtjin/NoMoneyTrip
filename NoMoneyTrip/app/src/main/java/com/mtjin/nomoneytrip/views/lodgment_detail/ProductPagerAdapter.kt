package com.mtjin.nomoneytrip.views.lodgment_detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mtjin.nomoneytrip.R

class ProductPagerAdapter(private val context: Context, private val items: List<String>) :
    RecyclerView.Adapter<ProductPagerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(items[position]).into(holder.imageUrl)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageUrl: ImageView = view.findViewById(R.id.iv_image)
    }
}