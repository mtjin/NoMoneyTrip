package com.mtjin.nomoneytrip.views.search

import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentSearchBinding
import com.mtjin.nomoneytrip.utils.TAG
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModel()

    override fun init() {
        binding.vm = viewModel
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            goLocal.observe(this@SearchFragment, Observer {
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToLocalpageFragment(it)
                )
            })
        }
    }
}