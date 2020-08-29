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
                var local = ""
                when (it) {
                    "seoul" -> local = "서울"
                    "incheon" -> local = "인천"
                    "daejeon" -> local = "대전"
                    "daegu" -> local = "대구"
                    "gwangju" -> local = "광주"
                    "busan" -> local = "부산"
                    "ulsan" -> local = "울산"
                    "sejong" -> local = "세종"
                    "gyeungi" -> local = "경기"
                    "kangwon" -> local = "강원"
                    "choongbuk" -> local = "충북"
                    "choongnam" -> local = "충남"
                    "gyungbuk" -> local = "경북"
                    "gyungnam" -> local = "경남"
                    "jeonbuk" -> local = "전북"
                    "jeonnam" -> local = "전남"
                    "jeju" -> local = "제주"
                }
                Log.d(TAG, "SearchFragment local -> $local")
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToLocalpageFragment(local)
                )
            })
        }
    }
}