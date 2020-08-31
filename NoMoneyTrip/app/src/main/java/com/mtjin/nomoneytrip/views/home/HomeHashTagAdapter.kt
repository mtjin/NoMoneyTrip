package com.mtjin.nomoneytrip.views.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.databinding.ItemHashTagBinding

class HomeHashTagAdapter :
    RecyclerView.Adapter<HomeHashTagAdapter.ViewHolder>() {
    private val items: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemHashTagBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_hash_tag,
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

    class ViewHolder(private val binding: ItemHashTagBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    fun addItems(items: List<String>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: String) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }
}