package com.mtjin.nomoneytrip.views.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.databinding.ItemFavoriteBinding

class FavoriteAdapter(
    private val onFavoriteClick: (Product) -> Unit
) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private val items = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemFavoriteBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_favorite,
            parent,
            false
        )
        val viewHolder = ViewHolder(binding)
        binding.ivSave.setOnClickListener {
            onFavoriteClick(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    fun addItems(items: List<Product>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }
}