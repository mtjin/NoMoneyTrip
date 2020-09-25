package com.mtjin.nomoneytrip.views.alarm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.data.alarm.Alarm
import com.mtjin.nomoneytrip.databinding.ItemAlarmBinding

class AlarmAdapter(private val onItemClick: (Alarm) -> Unit) :
    RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {
    private val items: ArrayList<Alarm> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemAlarmBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_alarm,
            parent,
            false
        )
        val viewHolder = ViewHolder(binding)
        binding.root.setOnClickListener {
            onItemClick(items[viewHolder.adapterPosition])
            items[viewHolder.adapterPosition].readState = true
        }
        return viewHolder
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].let {
            holder.bind(it)
        }
    }

    class ViewHolder(private val binding: ItemAlarmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Alarm) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    fun addItems(items: List<Alarm>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: Alarm) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }
}