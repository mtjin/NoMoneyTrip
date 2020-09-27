package com.mtjin.nomoneytrip.views.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.data.master_write.MasterLetter
import com.mtjin.nomoneytrip.databinding.ItemThankLetterBinding

class ProfileMasterLetterAdapter :
    RecyclerView.Adapter<ProfileMasterLetterAdapter.ViewHolder>() {
    private val items = ArrayList<MasterLetter>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemThankLetterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_thank_letter,
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

    inner class ViewHolder(private val binding: ItemThankLetterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MasterLetter) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    fun addItems(items: List<MasterLetter>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }
}