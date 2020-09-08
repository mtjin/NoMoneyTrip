package com.mtjin.nomoneytrip.views.reservation_history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.data.reservation_history.ReservationHistory
import com.mtjin.nomoneytrip.databinding.ItemReservationHistoryBinding
import com.mtjin.nomoneytrip.utils.getMyColor
import com.mtjin.nomoneytrip.utils.getTimestamp

class ReservationHistoryAdapter(private val context: Context) :
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

    inner class ViewHolder(private val binding: ItemReservationHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reservation: ReservationHistory) {
            binding.run {
                item = reservation
                when{
                    !reservation.reservation.state ->{
                        llLinear.visibility = View.GONE
                        tvState.text = "예약 취소"
                        tvState.setTextColor(context.getMyColor(R.color.colorRedEF4550))
                    }
                    reservation.reservation.endDateTimestamp < getTimestamp() ->{
                        llLinear.visibility = View.VISIBLE
                        tvState.text = "여행 완료"
                        tvState.setTextColor(context.getMyColor(R.color.colorOrangeF79256))
                        tvLeft.text = "봉사 인증"
                        tvRight.text = "리뷰 작성"
                    }
                    else ->{
                        llLinear.visibility = View.VISIBLE
                        tvState.text = "여행 접수"
                        tvState.setTextColor(context.getMyColor(R.color.colorOrangeF79256))
                        tvLeft.text = "예약 변경"
                        tvRight.text = "예약 취소"
                    }
                }
                executePendingBindings()
            }
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