package com.mtjin.nomoneytrip.views.reservation

import android.text.util.Linkify
import androidx.core.text.HtmlCompat
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentReservationBinding
import kotlinx.android.synthetic.main.fragment_reservation.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class ReservationFragment :
    BaseFragment<FragmentReservationBinding>(R.layout.fragment_reservation) {
    override fun init() {
        TODO("Not yet implemented")
    }

    fun initRuleText() {
        tv_consent_rule.text =
            HtmlCompat.fromHtml(
                getString(R.string.consent_use_text),
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
        Linkify.addLinks(tv_consent_rule, pattern1, "http://www.naver.com", null, transform)
        Linkify.addLinks(tv_consent_rule, pattern2, "http://www.google.com", null, transform)
        Linkify.addLinks(tv_consent_rule, pattern3, "https://youngest-programming.tistory.com/", null, transform)
    }
}
