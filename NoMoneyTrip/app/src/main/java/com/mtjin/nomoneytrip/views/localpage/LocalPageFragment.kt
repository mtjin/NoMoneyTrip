package com.mtjin.nomoneytrip.views.localpage

import android.util.Log
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentLocalPageBinding
import com.mtjin.nomoneytrip.utils.TAG

class LocalPageFragment : BaseFragment<FragmentLocalPageBinding>(R.layout.fragment_local_page) {
    private val safeArgs: LocalPageFragmentArgs by navArgs()
    override fun init() {
        processIntent()
        initAdapter()
    }

    private fun initAdapter() {

    }

    private fun processIntent() {
        Log.d(TAG, "LocalPageFragment safeArgs -> " + safeArgs.local)
        var local = ""
        when (safeArgs.local) {
            "seoul" -> {
                local = "서울"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.seoul
                    )
                )
            }
            "incheon" -> {
                local = "인천"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.incheon
                    )
                )
            }
            "daejeon" -> {
                local = "대전"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.daejeon
                    )
                )
            }
            "daegu" -> {
                local = "대구"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.daegu
                    )
                )
            }
            "gwangju" -> {
                local = "광주"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.gwangju
                    )
                )
            }
            "busan" -> {
                local = "부산"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.busan
                    )
                )
            }
            "ulsan" -> {
                local = "울산"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.ulsan
                    )
                )
            }
            "sejong" -> {
                local = "세종"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.sejong
                    )
                )
            }
            "gyeungi" -> {
                local = "경기"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.gyeungi
                    )
                )
            }
            "kangwon" -> {
                local = "강원"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.kangwon
                    )
                )
            }
            "choongbuk" -> {
                local = "충북"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.choongbuk
                    )
                )
            }
            "choongnam" -> {
                local = "충남"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.choongnam
                    )
                )
            }
            "gyungbuk" -> {
                local = "경북"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.gyungbuk
                    )
                )
            }
            "gyungnam" -> {
                local = "경남"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.gyungnam
                    )
                )
            }
            "jeonbuk" -> {
                local = "전북"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.jeonbuk
                    )
                )
            }
            "jeonnam" -> {
                local = "전남"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.jeonnam
                    )
                )
            }
            "jeju" -> {
                local = "제주"
                binding.ivLocal.setImageDrawable(
                    ContextCompat.getDrawable(
                        thisContext,
                        R.drawable.jeju
                    )
                )
            }
        }
        binding.tvLocalTitle.text = local

    }
}