package com.mtjin.nomoneytrip.views.master_main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.data.master_main.MasterProduct
import com.mtjin.nomoneytrip.databinding.ItemMasterReservationBinding

class MasterMainAdapter(
    private val context: Context,
    private val leftClick: (MasterProduct) -> Unit,
    private val rightClick: (MasterProduct) -> Unit
) :
    RecyclerView.Adapter<MasterMainAdapter.ViewHolder>() {
    private val items: ArrayList<MasterProduct> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMasterReservationBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_master_reservation,
            parent,
            false
        )
        val viewHolder = ViewHolder(binding)
        binding.tvLeft.setOnClickListener {//예약거절
            leftClick(items[viewHolder.adapterPosition])
        }
        binding.tvRight.setOnClickListener {//예약승인
            rightClick(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(private val binding: ItemMasterReservationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reservation: MasterProduct) {
            binding.run {
                item = reservation
                executePendingBindings()
            }
        }
    }

    fun addItems(items: List<MasterProduct>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: MasterProduct) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }
}