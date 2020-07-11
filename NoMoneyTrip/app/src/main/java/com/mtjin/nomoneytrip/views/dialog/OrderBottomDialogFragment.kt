package com.mtjin.nomoneytrip.views.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mtjin.nomoneytrip.R
import kotlinx.android.synthetic.main.fragment_order_bottom_dialog.view.*


class OrderBottomDialogFragment(val itemClick: (Int) -> Unit) : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.fragment_order_bottom_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        with(view) {
            tv_recommend_order.setOnClickListener {
                itemClick(0)
                dialog?.dismiss()
            }
            tv_review_order.setOnClickListener {
                itemClick(1)
                dialog?.dismiss()
            }
            tv_save_order.setOnClickListener {
                itemClick(2)
                dialog?.dismiss()
            }
            tv_price_low_order.setOnClickListener {
                itemClick(3)
                dialog?.dismiss()
            }
            tv_price_high_order.setOnClickListener {
                itemClick(4)
                dialog?.dismiss()
            }
        }
    }
}