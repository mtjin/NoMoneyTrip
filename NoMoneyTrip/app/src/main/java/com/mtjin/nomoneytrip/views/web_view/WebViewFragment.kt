package com.mtjin.nomoneytrip.views.web_view

import android.annotation.SuppressLint
import android.view.KeyEvent
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseFragment
import com.mtjin.nomoneytrip.databinding.FragmentWebViewBinding

class WebViewFragment : BaseFragment<FragmentWebViewBinding>(R.layout.fragment_web_view) {
    private val args: WebViewFragmentArgs by navArgs()
    private lateinit var mWebSettings: WebSettings
    private var url: String? = ""
    override fun init() {
        processIntent()
        initWebView()
        loadWebView()
    }

    private fun initWebView() {
        binding.wvWebview.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event?.action != KeyEvent.ACTION_DOWN)
                    return true
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (binding.wvWebview.canGoBack()) {
                        binding.wvWebview.goBack()
                    } else {
                        requireActivity().onBackPressed()
                    }

                    return true
                }
                return false
            }
        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebView() {
        binding.wvWebview.webViewClient = WebViewClient() // 클릭시 새창 안뜨게
        mWebSettings = binding.wvWebview.settings //세부 세팅 등록
        mWebSettings.javaScriptEnabled = true // 웹페이지 자바스클비트 허용 여부
        mWebSettings.setSupportMultipleWindows(false) // 새창 띄우기 허용 여부
        mWebSettings.javaScriptCanOpenWindowsAutomatically = false // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings.loadWithOverviewMode = true // 메타태그 허용 여부
        mWebSettings.useWideViewPort = true // 화면 사이즈 맞추기 허용 여부
        mWebSettings.setSupportZoom(true) // 화면 줌 허용 여부
        mWebSettings.builtInZoomControls = false // 화면 확대 축소 허용 여부
        mWebSettings.cacheMode = WebSettings.LOAD_NO_CACHE // 브라우저 캐시 허용 여부
        mWebSettings.domStorageEnabled = true // 로컬저장소 허용 여부
        binding.wvWebview.loadUrl(url.toString()) // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작
    }

    private fun processIntent() {
        url = args.link
    }

}