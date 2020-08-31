package com.mtjin.nomoneytrip.views.home

import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var homeAdapter: HomeProductAdapter
    private lateinit var hashTagAdapter: HomeHashTagAdapter

    override fun init() {
        binding.vm = viewModel
        initViewModelCallback()
        initAdapter()
        initView()
    }

    private fun initAdapter() {
        //상단 해쉬태그 어댑터
        hashTagAdapter = HomeHashTagAdapter()
        binding.rvHashTags.adapter = hashTagAdapter

        //하단 아이템 리스트
        homeAdapter = HomeProductAdapter()
        binding.rvHomeProducts.adapter = homeAdapter
    }

    private fun initView() {
        viewModel.requestProducts()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            goSearch.observe(this@HomeFragment, Observer {
                val direction: NavDirections =
                    HomeFragmentDirections.actionBottomNav1ToSearchFragment()
                findNavController().navigate(direction)
            })
        }
    }

}