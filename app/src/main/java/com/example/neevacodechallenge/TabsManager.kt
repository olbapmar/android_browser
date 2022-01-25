package com.example.neevacodechallenge

import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient

class TabsManager {

    interface URLChangeListener {
        fun onURLChanged(newURL: String)
    }

    constructor(webView: WebView, listener: URLChangeListener) {
        urlChangeListener = listener
        webViews = arrayListOf(webView)
        activeWebView = webView
        activeWebView.webViewClient = object : WebViewClient() {
            override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                if (url != null) {
                    listener.onURLChanged(url)
                }
                super.doUpdateVisitedHistory(view, url, isReload)
            }
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

    private val urlChangeListener: URLChangeListener
    private val webViews: ArrayList<WebView>
    private val activeWebView : WebView
}