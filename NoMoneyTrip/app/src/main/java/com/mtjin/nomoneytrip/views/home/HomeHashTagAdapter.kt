package com.mtjin.nomoneytrip.views.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.databinding.ItemHashTagBinding
import com.mtjin.nomoneytrip.utils.getMyColor
import com.mtjin.nomoneytrip.utils.getMyDrawable
import kotlinx.android.synthetic.main.item_hash_tag.view.*

open class HomeHashTagAdapter(
    private val hashTagClick: (String) -> Unit,
    private val context: Context
) :
    RecyclerView.Adapter<HomeHashTagAdapter.ViewHolder>() {
    private val items = ArrayList<String>()
    private val viewList = ArrayList<TextView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemHashTagBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_hash_tag,
            parent,
            false
        )
        val viewHolder = ViewHolder(binding)
        binding.root.setOnClickListener {
            hashTagClick(items[viewHolder.adapterPosition])
            for (view in viewList) {
                if (view == it.tv_hash_tag) {
                    binding.tvHashTag.background =
                        context.getMyDrawable(R.drawable.bg_hash_tag_solid_orange_radius_8dp)
                    binding.tvHashTag.setTextColor(context.getMyColor(R.color.colorWhite))
                } else {
                    view.tv_hash_tag.background =
                        context.getMyDrawable(R.drawable.bg_hash_tag_orange_radius_8dp)
                    view.tv_hash_tag.setTextColor(context.getMyColor(R.color.colorOrangeF79256))
                }
            }
        }
        if (!viewList.contains(binding.root)) {
            viewList.add(binding.tvHashTag)
        }
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