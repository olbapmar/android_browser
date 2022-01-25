package com.example.neevacodechallenge

import android.content.res.Resources
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import java.text.FieldPosition

class TabsManager(listener: ClientActivity, defaultUrl: String) {

    interface ClientActivity {
        fun onURLChanged(newURL: String)
        fun createWebView() : WebView
        fun showWebView(webView: WebView)
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
        loadWebPage(defaultUrl)
    }

    fun closeTab(position: Int) {
        if (position >= 0 && position < webViews.size) {
            if (webViews[position] == activeWebView) {
                if (webViews.size > 1) {
                    activeWebView = webViews[(position + 1) % webViews.size]
                } else {
                    addTab(true)
                }
            }

            webViews.remove(webViews[position])
            clientActivity.showWebView(activeWebView)
        }
    }

    fun selectTab(position: Int) {
        if (position >= 0 && position < webViews.size) {
            activeWebView = webViews[position]
            clientActivity.showWebView(activeWebView)
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

    private val clientActivity: ClientActivity = listener
    val webViews: ArrayList<WebView> = ArrayList()
    lateinit var activeWebView : WebView
    val defaultUrl : String = defaultUrl
}