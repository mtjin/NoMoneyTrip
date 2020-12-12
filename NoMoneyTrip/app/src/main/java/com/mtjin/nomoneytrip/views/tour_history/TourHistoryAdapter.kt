package com.mtjin.nomoneytrip.views.tour_history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.data.reservation_history.ReservationProduct
import com.mtjin.nomoneytrip.databinding.ItemTourWriteHistoryBinding
import com.mtjin.nomoneytrip.utils.extensions.getMyDrawable

class TourHistoryAdapter(
    private val context: Context,
    private val itemClick: (ReservationProduct) -> Unit
) :
    RecyclerView.Adapter<TourHistoryAdapter.ViewHolder>() {
    private val items = ArrayList<ReservationProduct>()
    private val viewList = ArrayList<ItemTourWriteHistoryBinding>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemTourWriteHistoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_tour_write_history,
            parent,
            false
        )
        val viewHolder = ViewHolder(binding)
        binding.root.setOnClickListener {
            itemClick(items[viewHolder.adapterPosition])
            binding.clConstraint.background =
                context.getMyDrawable(R.drawable.bg_orange_stroke_garyf4f4_solid_radius_8dp)
            for (view in viewList) {
                if (view.item?.reservation?.id != binding.item?.reservation?.id) {
                    view.clConstraint.background =
                        context.getMyDrawable(R.drawable.bg_solid_grayf4f4_radius_8dp)
                }
            }
        }
        viewList.add(binding)
        return viewHolder
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