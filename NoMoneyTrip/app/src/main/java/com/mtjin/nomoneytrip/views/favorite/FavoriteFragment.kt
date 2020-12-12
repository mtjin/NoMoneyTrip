package com.mtjin.nomoneytrip.views.favorite

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentFavoriteBinding
import com.mtjin.nomoneytrip.utils.uuid
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {
    private val viewModel: FavoriteViewModel by viewModel()
    lateinit var adapter: FavoriteAdapter
    override fun init() {
        binding.vm = viewModel
        initViewModelCallback()
        initAdapter()
        requestFavorites()
    }

    private fun initAdapter() {
        adapter = FavoriteAdapter(onItemClick = {
            findNavController().navigate(
                FavoriteFragmentDirections.actionFavoriteFragmentToLodgmentDetailFragment(it)
            )
        }, onFavoriteClick = {
            (it.favoriteList as ArrayList).remove(uuid)
            viewModel.updateProductFavorite(it)
        })
        binding.rvProducts.adapter = adapter
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            backClick.observe(this@FavoriteFragment, Observer {
                findNavController().popBackStack()
            })
            productList.observe(
                this@FavoriteFragment, Observer {//데이터바인딩어댑터로 처음에했는데 계속안되고 이것만 되는데 원인을 모르겠음
                    adapter.clear()
                    adapter.addItems(it)
                })
        }
    }

    private fun requestFavorites() {
        viewModel.requestMyFavorites()
    }

}