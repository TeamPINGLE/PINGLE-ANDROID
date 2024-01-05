package org.sopt.pingle.presentation.ui.common

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityWebViewBinding
import org.sopt.pingle.util.base.BindingActivity

class WebViewActivity : BindingActivity<ActivityWebViewBinding>(R.layout.activity_web_view) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        loadWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initLayout() {
        binding.wvWebView.apply {
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()

            settings.apply {
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                loadWithOverviewMode = true
                useWideViewPort = true
                domStorageEnabled = true
            }
        }
    }

    private fun loadWebView() {
        intent.getStringExtra(WEB_VIEW_LINK)?.let { binding.wvWebView.loadUrl(it) }
    }

    companion object {
        const val WEB_VIEW_LINK = "WebViewLink"
    }
}
