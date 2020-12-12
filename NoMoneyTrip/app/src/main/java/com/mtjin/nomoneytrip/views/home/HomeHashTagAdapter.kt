package com.mtjin.nomoneytrip.views.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.databinding.ItemHashTagBinding
import com.mtjin.nomoneytrip.utils.extensions.getMyColor
import com.mtjin.nomoneytrip.utils.extensions.getMyDrawable

open class HomeHashTagAdapter(
    private val hashTagClick: (String) -> Unit,
    private val context: Context
) :
    RecyclerView.Adapter<HomeHashTagAdapter.ViewHolder>() {
    private val items = ArrayList<String>()
    private val viewHolders = HashSet<ViewHolder>()
    var clickItem = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemHashTagBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_hash_tag,
            parent,
            false
        )
        val viewHolder = ViewHolder(binding)
        binding.root.setOnClickListener {
            if (items[viewHolder.adapterPosition] == clickItem) {
                binding.tvHashTag.background =
                    context.getMyDrawable(R.drawable.bg_hash_tag_orange_radius_8dp)
                binding.tvHashTag.setTextColor(context.getMyColor(R.color.colorOrangeF79256))
                hashTagClick("")
                clickItem = ""
            } else {
                clickItem = items[viewHolder.adapterPosition]
                binding.tvHashTag.background =
                    context.getMyDrawable(R.drawable.bg_hash_tag_solid_orange_radius_8dp)
                binding.tvHashTag.setTextColor(context.getMyColor(R.color.colorWhite))
                for (holder in viewHolders) {
                    if (holder.adapterPosition > -1) {
                        holder.bind(items[holder.adapterPosition])
                    }
                }
                hashTagClick(items[viewHolder.adapterPosition])
            }
        }
        viewHolders.add(viewHolder)
        return viewHolder
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(private val binding: ItemHashTagBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.item = item
            if (item == clickItem) {
                binding.tvHashTag.background =
                    context.getMyDrawable(R.drawable.bg_hash_tag_solid_orange_radius_8dp)
                binding.tvHashTag.setTextColor(context.getMyColor(R.color.colorWhite))
            } else {
                binding.tvHashTag.background =
                    context.getMyDrawable(R.drawable.bg_hash_tag_orange_radius_8dp)
                binding.tvHashTag.setTextColor(context.getMyColor(R.color.colorOrangeF79256))
            }
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