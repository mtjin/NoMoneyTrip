package com.mtjin.nomoneytrip.views.reservation

import android.text.util.Linkify
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentReservationBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Matcher
import java.util.regex.Pattern

class ReservationFragment :
    BaseFragment<FragmentReservationBinding>(R.layout.fragment_reservation) {
    private val args: ReservationFragmentArgs by navArgs()
    private val viewModel: ReservationViewModel by viewModel()

    override fun init() {
        binding.vm = viewModel
        processIntent()
        initRuleText()
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            consentMsg.observe(this@ReservationFragment, Observer {
                showToast(getString(R.string.please_consent_msg))
            })

            successReservation.observe(this@ReservationFragment, Observer {
                if (it) {
                    findNavController().navigate(
                        ReservationFragmentDirections.actionReservationFragmentToReservationCompleteFragment(
                            reservation
                        )
                    )
                } else {
                    showToast(getString(R.string.reservation_fail_msg))
                }
            })

            duplicateDateMsg.observe(this@ReservationFragment, Observer {
                showToast(getString(R.string.duplicate_date_select_another_date_msg))
            })

            backClick.observe(this@ReservationFragment, Observer {
                findNavController().popBackStack()
            })
        }
    }

    private fun processIntent() {
        binding.product = args.product
        binding.reservation = args.reservation
        viewModel.product = args.product
        viewModel.reservation = args.reservation
    }

    private fun initRuleText() {
        binding.tvConsentRule.text =
            HtmlCompat.fromHtml(
                getString(R.string.consent_rule_text),
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
        val transform =
            Linkify.TransformFilter(object : Linkify.TransformFilter, (Matcher, String) -> String {
                override fun transformUrl(p0: Matcher?, p1: String?): String {
                    return ""
                }

                override fun invoke(p1: Matcher, p2: String): String {
                    return ""
                }

            })
        //링크달 패턴 정의
        val pattern1 = Pattern.compile("취소 / 이용규정")
        val pattern2 = Pattern.compile("개인 정보 수집 / 이용 방침")
        val pattern3 = Pattern.compile("개인정보 제 3자 제공")
        Linkify.addLinks(binding.tvConsentRule, pattern1, "http://www.naver.com", null, transform)
        Linkify.addLinks(binding.tvConsentRule, pattern2, "http://www.google.com", null, transform)
        Linkify.addLinks(
            binding.tvConsentRule,
            pattern3,
            "https://youngest-programming.tistory.com/",
            null,
            transform
        )
    }
}
