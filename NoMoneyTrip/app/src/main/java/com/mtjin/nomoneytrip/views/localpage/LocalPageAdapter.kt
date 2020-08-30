package com.mtjin.nomoneytrip.views.localpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.data.local_page.TourIntroduce
import com.mtjin.nomoneytrip.data.local_page.TourIntroduceResponseBody
import com.mtjin.nomoneytrip.databinding.ItemTourIntroduceBinding

class LocalPageAdapter :
    RecyclerView.Adapter<LocalPageAdapter.ViewHolder>() {
    private val items: ArrayList<TourIntroduce> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemTourIntroduceBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_tour_introduce,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].let {
            holder.bind(it)
        }
    }

    class ViewHolder(private val binding: ItemTourIntroduceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TourIntroduce) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    fun addItems(items: List<TourIntroduce>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: TourIntroduce) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }
}