package com.mtjin.nomoneytrip.views.master_main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.data.master_main.MasterProduct
import com.mtjin.nomoneytrip.databinding.ItemMasterReservationBinding
import com.mtjin.nomoneytrip.utils.getTimestamp

class MasterMainAdapter(
    private val context: Context,
    private val leftClick: (MasterProduct) -> Unit,
    private val rightClick: (MasterProduct) -> Unit,
    private val messageClick: (MasterProduct) -> Unit
) :
    RecyclerView.Adapter<MasterMainAdapter.ViewHolder>() {
    private val items: ArrayList<MasterProduct> = ArrayList()
    private val timestamp = getTimestamp()

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
        binding.ivMessage.setOnClickListener {
            messageClick(items[viewHolder.adapterPosition])
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

        fun bind(masterProduct: MasterProduct) {
            binding.run {
                item = masterProduct
                if (masterProduct.reservation.state == 2 && masterProduct.reservation.endDateTimestamp < timestamp) {
                    ivMessage.visibility = View.VISIBLE
                    tvReservationState.visibility = View.GONE
                } else {
                    ivMessage.visibility = View.GONE
                    tvReservationState.visibility = View.VISIBLE
                }
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