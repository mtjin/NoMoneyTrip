package com.mtjin.nomoneytrip.views.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mtjin.nomoneytrip.R
import kotlinx.android.synthetic.main.fragment_rating_bottom_dialog.view.*

class RatingBottomDialogFragment(
    val ratingClick: (Float) -> Unit
) :
    BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.fragment_rating_bottom_dialog, container, false)

    companion object {
        fun newInstance(
            ratingClick: (Float) -> Unit
        ): RatingBottomDialogFragment {
            return RatingBottomDialogFragment(ratingClick)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.rb_rating.setOnClickListener {
            ratingClick(view.rb_rating.rating)
            dialog?.dismiss()
        }
    }
}
