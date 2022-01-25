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
        webViews = ArrayList()
        activeWebView = webView

        addTab(webView, true)
    }

    fun addTab(webView: WebView, setAsActive: Boolean) {
        webViews.add(webView)

        webView.webViewClient = object : WebViewClient() {
            override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                if (url != null && view != null && view == activeWebView) {
                    urlChangeListener.onURLChanged(url)
                }
                super.doUpdateVisitedHistory(view, url, isReload)
            }
        }

        if (setAsActive) {
            activeWebView = webView
            urlChangeListener.onURLChanged(activeWebView.url ?: "")
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
    val webViews: ArrayList<WebView>
    var activeWebView : WebView
}