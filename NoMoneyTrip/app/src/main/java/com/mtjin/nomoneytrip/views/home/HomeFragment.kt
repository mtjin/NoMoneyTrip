package com.mtjin.nomoneytrip.views.home

import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

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
        //해쉬태그 어댑터
        hashTagAdapter = HomeHashTagAdapter({ hashTag ->
            viewModel.requestHashTagProducts(hashTag)
        }, thisContext)
        binding.rvHashTags.adapter = hashTagAdapter

        //상품 리스트 어댑터
        homeAdapter = HomeProductAdapter(context = thisContext, itemClick = {
            findNavController().navigate(
                HomeFragmentDirections.actionBottomNav1ToLodgmentDetailFragment(
                    it
                )
            )
        }, favoriteClick = {
            viewModel.updateProductFavorite(it)
        })
        binding.rvHomeProducts.adapter = homeAdapter
    }

    private fun initView() {
        viewModel.requestProducts()
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) { // 접혔을때
                binding.ivToolbarSearch.visibility = View.VISIBLE
            } else {// 펴졌을때
                binding.ivToolbarSearch.visibility = View.GONE
            }
        })
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            goSearch.observe(this@HomeFragment, Observer {
                val direction: NavDirections =
                    HomeFragmentDirections.actionBottomNav1ToSearchFragment()
                findNavController().navigate(direction)
            })

            goAlarm.observe(this@HomeFragment, Observer {
                val direction: NavDirections =
                    HomeFragmentDirections.actionBottomNav1ToAlarmFragment()
                findNavController().navigate(direction)
            })
        }
    }

}