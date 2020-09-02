package com.mtjin.nomoneytrip.views.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.databinding.ItemUserReviewBinding

class CommunityAdapter :
    RecyclerView.Adapter<CommunityAdapter.ViewHolder>() {
    private val items = ArrayList<UserReview>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemUserReviewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_user_review,
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

    class ViewHolder(private val binding: ItemUserReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserReview) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    fun addItems(items: List<UserReview>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }
}