package com.mtjin.nomoneytrip.views.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mtjin.nomoneytrip.R
import kotlinx.android.synthetic.main.fragment_dialog_yes_no.view.*

class QuestionDialogFragment(
    private val question: String,
    private val leftText: String,
    private val rightText: String,
    private val yesClick: (Boolean) -> Unit
) : DialogFragment(),
    View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dialog_yes_no, container, false)
        view.tv_question.text = question
        view.tv_no.text = leftText
        view.tv_yes.text = rightText
        view.tv_yes.setOnClickListener {
            yesClick(true)
            dismiss()
        }
        view.tv_no.setOnClickListener {
            dismiss()
        }

        return view
    }

    companion object {
        fun getInstance(
            question: String,
            leftText: String,
            rightText: String, yesClick: (Boolean) -> Unit
        ): QuestionDialogFragment {
            return QuestionDialogFragment(question, leftText, rightText, yesClick)
        }
    }

    override fun onClick(p0: View?) {
        dismiss()
    }
}
