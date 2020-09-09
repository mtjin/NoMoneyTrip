package com.mtjin.nomoneytrip.views.tour_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.data.reservation_history.ReservationProduct
import com.mtjin.nomoneytrip.databinding.ItemTourWriteHistoryBinding

class TourHistoryAdapter :
    RecyclerView.Adapter<TourHistoryAdapter.ViewHolder>() {
    private val items: ArrayList<ReservationProduct> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemTourWriteHistoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_tour_write_history,
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

    class ViewHolder(private val binding: ItemTourWriteHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ReservationProduct) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    fun addItems(items: List<ReservationProduct>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: ReservationProduct) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }
}