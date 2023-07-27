package com.veasnachhom.androidzoo.attractionPlace.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.android.material.appbar.MaterialToolbar
import com.veasnachhom.androidzoo.databinding.FragmentWebviewBinding
import com.veasnachhom.androidzoo.ui.fragment.BaseFragment


class WebViewFragment : BaseFragment<FragmentWebviewBinding>() {

    private var url: String? = null

    override fun getToolbar(): MaterialToolbar? {
        return binding.toolbar
    }

    override fun inflateLayout(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentWebviewBinding = FragmentWebviewBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        url?.let { url ->
            setToolbarTitle(url)
            binding.webview.run {
                webViewClient = (object : WebViewClient() {
                    @Deprecated("Deprecated in Java")
                    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                        view.loadUrl(url)
                        return false
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        binding.progressBar.postDelayed({
                            binding.progressBar.apply {
                                visibility = View.GONE
                            }
                        }, 1000)
                    }
                })
                webChromeClient = (object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        super.onProgressChanged(view, newProgress)
                        binding.progressBar.setProgress(newProgress, true)
                    }
                })
                loadUrl(url)
            }
        }
    }

    companion object {

        fun newInstance(url: String?): WebViewFragment {
            val fragment = WebViewFragment()
            fragment.url = url

            return fragment
        }
    }
}