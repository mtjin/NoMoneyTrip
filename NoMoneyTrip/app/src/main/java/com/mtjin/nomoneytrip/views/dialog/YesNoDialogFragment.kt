package com.mtjin.nomoneytrip.views.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mtjin.nomoneytrip.R
import kotlinx.android.synthetic.main.fragment_dialog_yes_no.view.*

class YesNoDialogFragment : DialogFragment(),
    View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dialog_yes_no, container, false)
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
        lateinit var yesClick: (Boolean) -> Unit
        fun getInstance(yesClick: (Boolean) -> Unit): YesNoDialogFragment {
            this.yesClick = yesClick
            return YesNoDialogFragment()
        }
    }

    override fun onClick(p0: View?) {
        dismiss()
    }
}
