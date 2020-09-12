package com.mtjin.nomoneytrip.views.community

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.databinding.ItemUserReviewBinding
import com.mtjin.nomoneytrip.utils.getMyDrawable
import com.mtjin.nomoneytrip.utils.uuid


class CommunityAdapter(
    private val context: Context,
    private val recommendClick: (UserReview) -> Unit
) :
    RecyclerView.Adapter<CommunityAdapter.ViewHolder>() {
    private val items = ArrayList<UserReview>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemUserReviewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_user_review,
            parent,
            false
        )
        val viewHolder = ViewHolder(binding)
        binding.tvHeart.setOnClickListener {
            items[viewHolder.adapterPosition].let {
                if (it.review.recommendList.contains(uuid)) {
                    val img: Drawable? = context.getMyDrawable(R.drawable.ic_community_good_off)
                    binding.tvHeart.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null)
                    binding.tvHeart.text = (binding.tvHeart.text.toString().toInt() - 1).toString()
                    items[viewHolder.adapterPosition].review.recommendList.remove(uuid)
                    recommendClick(items[viewHolder.adapterPosition])
                } else {
                    val img: Drawable? = context.getMyDrawable(R.drawable.ic_community_good)
                    binding.tvHeart.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null)
                    binding.tvHeart.text = (binding.tvHeart.text.toString().toInt() + 1).toString()
                    items[viewHolder.adapterPosition].review.recommendList.add(uuid)
                    recommendClick(items[viewHolder.adapterPosition])
                }
            }
        }
        return viewHolder
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(private val binding: ItemUserReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserReview) {
            binding.item = item
            if (item.review.recommendList.contains(uuid)) {
                val img: Drawable? = context.getMyDrawable(R.drawable.ic_community_good)
                binding.tvHeart.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null)
            }
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