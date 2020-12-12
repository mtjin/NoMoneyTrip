package com.mtjin.nomoneytrip.views.home

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.databinding.ItemProductBinding
import com.mtjin.nomoneytrip.utils.extensions.getMyDrawable
import com.mtjin.nomoneytrip.utils.uuid

class HomeProductAdapter(
    private val context: Context,
    private val itemClick: (Product) -> Unit,
    private val favoriteClick: (Product) -> Unit
) :
    RecyclerView.Adapter<HomeProductAdapter.ViewHolder>() {
    private val items: ArrayList<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemProductBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_product,
            parent,
            false
        )
        val viewHolder = ViewHolder(binding)
        binding.root.setOnClickListener {
            itemClick(items[viewHolder.adapterPosition])
        }
        binding.ivFavorite.setOnClickListener {
            items[viewHolder.adapterPosition].let {
                if (it.favoriteList.contains(uuid)) {
                    val img: Drawable? = context.getMyDrawable(R.drawable.ic_save_white_off)
                    binding.ivFavorite.setImageDrawable(img)
                    (items[viewHolder.adapterPosition].favoriteList as ArrayList).remove(uuid)
                } else {
                    val img: Drawable? = context.getMyDrawable(R.drawable.ic_save_on)
                    (items[viewHolder.adapterPosition].favoriteList as ArrayList).add(uuid)
                    binding.ivFavorite.setImageDrawable(img)
                }
                favoriteClick(items[viewHolder.adapterPosition])
            }
        }
        return viewHolder
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            val adapter = ProductHashTagAdapter()
            binding.rvHashTags.adapter = adapter
            binding.item = item
            if (item.favoriteList.contains(uuid)) {
                val img: Drawable? = context.getMyDrawable(R.drawable.ic_save_on)
                binding.ivFavorite.setImageDrawable(img)
            }else{
                val img: Drawable? = context.getMyDrawable(R.drawable.ic_save_white_off)
                binding.ivFavorite.setImageDrawable(img)
            }
            binding.executePendingBindings()
        }
    }

    fun addItems(items: List<Product>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: Product) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }
}