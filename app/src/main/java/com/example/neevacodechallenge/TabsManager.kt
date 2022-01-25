package com.example.neevacodechallenge

import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient

class TabsManager {

    interface ClientActivity {
        fun onURLChanged(newURL: String)
        fun createWebView() : WebView
        fun showWebView(webView: WebView)
    }

    constructor(listener: ClientActivity) {
        clientActivity = listener
    }

    fun addTab(setAsActive: Boolean) {
        val webView = clientActivity.createWebView()

        webViews.add(webView)

        webView.webViewClient = object : WebViewClient() {
            override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                if (url != null && view != null && view == activeWebView) {
                    clientActivity.onURLChanged(url)
                }
                super.doUpdateVisitedHistory(view, url, isReload)
            }
        }

        if (setAsActive) {
            activeWebView = webView
            clientActivity.showWebView(webView)
            clientActivity.onURLChanged(activeWebView.url ?: "")
        }
    }

    fun goBack() {
        if (activeWebView.canGoBack()) {
            activeWebView.goBack()
        }
    }

    fun goForward() {
        if (activeWebView.canGoForward()) {
            activeWebView.goForward()
        }
    }

    fun reload() {
        activeWebView.reload()
    }

    fun numberOfTabs(): Int{
        return webViews.size
    }

    @Throws(UnsupportedOperationException::class) private fun buildUri(authority: String) : Uri {
        val builder = Uri.Builder()
        builder.scheme("https").authority(authority)

        return builder.build()
    }

    fun loadWebPage(text: String) {

        var urlText = text
        activeWebView.settings.javaScriptEnabled = true

        try {
            if (!text.startsWith("http://") && !text.startsWith("https://")) {
                urlText = buildUri(urlText).toString()
            }
            activeWebView.loadUrl(urlText)
        } catch (e: UnsupportedOperationException) {
            e.printStackTrace()
        }
    }

    private val clientActivity: ClientActivity
    val webViews: ArrayList<WebView> = ArrayList()
    lateinit var activeWebView : WebView
}