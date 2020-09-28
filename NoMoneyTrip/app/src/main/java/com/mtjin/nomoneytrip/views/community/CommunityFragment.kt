package com.mtjin.nomoneytrip.views.community

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentCommunityBinding
import com.mtjin.nomoneytrip.utils.getMyColor
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommunityFragment : BaseFragment<FragmentCommunityBinding>(R.layout.fragment_community) {
    private val viewModel: CommunityViewModel by viewModel()
    override fun init() {
        binding.vm = viewModel
        initViewModelCallback()
        initAdapter()
        initView()
    }

    private fun initView() {
        val cities = thisContext.resources.getStringArray(R.array.cities)
        val cityArrayAdapter =
            ArrayAdapter(thisContext, R.layout.support_simple_spinner_dropdown_item, cities)
        binding.spCities.adapter = cityArrayAdapter
        binding.spCities.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                view?.let {
                    viewModel.requestReviews((parent.getChildAt(0) as TextView).text.toString())
                    (parent.getChildAt(0) as TextView).setTextColor(thisContext.getMyColor(R.color.colorOrangeF79256))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun initAdapter() {
        binding.rvReviews.adapter = CommunityAdapter(context = thisContext, recommendClick = {
            viewModel.updateReviewRecommend(it)
        }, productClick = {
            findNavController().navigate(
                CommunityFragmentDirections.actionBottomNav2ToLodgmentDetailFragment(
                    it.product
                )
            )
        })
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            goTourHistory.observe(this@CommunityFragment, Observer {
                Log.d("AAAAA", it.toString())
                findNavController().navigate(
                    CommunityFragmentDirections.actionBottomNav2ToTourHistoryFragment(it.toTypedArray())
                )
            })

            goTourNoHistory.observe(this@CommunityFragment, Observer {
                findNavController().navigate(CommunityFragmentDirections.actionBottomNav2ToTourNoHistoryFragment())
            })
        }
    }

}