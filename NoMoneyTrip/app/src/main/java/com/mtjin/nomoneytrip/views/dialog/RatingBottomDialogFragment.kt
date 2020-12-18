package com.mtjin.nomoneytrip.views.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mtjin.nomoneytrip.R
import kotlinx.android.synthetic.main.fragment_rating_bottom_dialog.view.*

class RatingBottomDialogFragment :
    BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.fragment_rating_bottom_dialog, container, false)

    companion object {
        lateinit var ratingClick: (Float) -> Unit
        fun newInstance(
            ratingClick: (Float) -> Unit
        ): RatingBottomDialogFragment {
            this.ratingClick = ratingClick
            return RatingBottomDialogFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.rb_rating.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { p0, rating, p2 ->
                ratingClick(rating)
                dialog?.dismiss()
            }
    }
}
