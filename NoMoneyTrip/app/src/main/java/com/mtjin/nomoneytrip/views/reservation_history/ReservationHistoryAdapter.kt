package com.mtjin.nomoneytrip.views.reservation_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.data.reservation_history.ReservationHistory
import com.mtjin.nomoneytrip.databinding.ItemReservationHistoryBinding

class ReservationHistoryAdapter :
    RecyclerView.Adapter<ReservationHistoryAdapter.ViewHolder>() {
    private val items: ArrayList<ReservationHistory> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemReservationHistoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_reservation_history,
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

    class ViewHolder(private val binding: ItemReservationHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reservation: ReservationHistory) {
            binding.item = reservation
            binding.executePendingBindings()
        }
    }

    fun addItems(items: List<ReservationHistory>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: ReservationHistory) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }
}