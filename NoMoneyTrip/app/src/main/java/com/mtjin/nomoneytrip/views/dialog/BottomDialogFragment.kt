package com.mtjin.nomoneytrip.views.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mtjin.nomoneytrip.R
import kotlinx.android.synthetic.main.fragment_bottom_dialog.view.*

class BottomDialogFragment(
    private val question: String,
    val itemClick: (Boolean) -> Unit
) :
    BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.fragment_bottom_dialog, container, false)

    companion object {
        fun newInstance(
            question: String,
            itemClick: (Boolean) -> Unit
        ): BottomDialogFragment {
            return BottomDialogFragment(question, itemClick)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.text_question.text = question
        view.tv_yes.setOnClickListener {
            itemClick(true)
            dialog?.dismiss()
        }
        view.tv_no.setOnClickListener {
            itemClick(false)
            dialog?.dismiss()
        }
    }
}
