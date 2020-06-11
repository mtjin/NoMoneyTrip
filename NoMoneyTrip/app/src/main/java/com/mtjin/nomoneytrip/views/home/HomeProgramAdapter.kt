package com.mtjin.nomoneytrip.views.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.data.home.GuideTourItem
import com.mtjin.nomoneytrip.databinding.ItemGuideTourBinding

class HomeProgramAdapter(
    private val itemClick: (Any) -> Unit
) :
    RecyclerView.Adapter<HomeProgramAdapter.ViewHolder>() {

    private val items: ArrayList<GuideTourItem> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeProgramAdapter.ViewHolder {
        val binding: ItemGuideTourBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_guide_tour,
            parent,
            false
        )
        val viewHolder = ViewHolder(binding)
        binding.root.setOnClickListener {
            itemClick(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: HomeProgramAdapter.ViewHolder, position: Int) {
        items[position].let {
            holder.bind(it)
        }
    }

    class ViewHolder(private val binding: ItemGuideTourBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Any) {
            //binding.item = item
            binding.executePendingBindings()
        }
    }

    fun addItems(items: List<GuideTourItem>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }

}