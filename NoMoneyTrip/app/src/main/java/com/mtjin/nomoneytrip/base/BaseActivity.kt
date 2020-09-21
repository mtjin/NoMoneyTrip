package com.mtjin.nomoneytrip.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.mtjin.nomoneytrip.views.dialog.LottieDialogFragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes val layoutId: Int
) : AppCompatActivity() {
    lateinit var binding: B
    private val compositeDisposable = CompositeDisposable()
    lateinit var lottieDialog: LottieDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        lottieDialog = LottieDialogFragment.newInstance()
    }

    protected fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    fun showProgressDialog() {
        lottieDialog.show(
            this.supportFragmentManager,
            lottieDialog.tag
        )
    }

    fun hideProgressDialog() {
        if (lottieDialog.isAdded) {
            lottieDialog.dismissAllowingStateLoss()
        }
    }
}

