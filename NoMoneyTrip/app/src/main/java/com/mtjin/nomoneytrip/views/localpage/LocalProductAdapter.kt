package com.mtjin.nomoneytrip.views.localpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.databinding.ItemLocalProductBinding
import com.mtjin.nomoneytrip.views.home.ProductHashTagAdapter

class LocalProductAdapter :
    RecyclerView.Adapter<LocalProductAdapter.ViewHolder>() {
    private val items: ArrayList<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemLocalProductBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_local_product,
            parent,
            false
        )
        return ViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].let {
            holder.bind(it)
        }
    }

    class ViewHolder(private val binding: ItemLocalProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            val adapter = ProductHashTagAdapter()
            binding.rvHashTags.adapter = adapter
            binding.item = item
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