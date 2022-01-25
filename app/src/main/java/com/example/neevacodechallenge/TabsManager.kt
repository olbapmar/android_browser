package com.example.neevacodechallenge

import android.net.Uri
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.drawToBitmap

class TabsManager(listener: ClientActivity, defaultUrl: String) {

    interface ClientActivity {
        fun onURLChanged(newURL: String)
        fun createWebView() : WebView
        fun showWebView(webView: WebView)
        fun onProgressChanged(progress: Int)
    }

    fun addTab(setAsActive: Boolean) {
        val webView = clientActivity.createWebView()

        val tab = Tab(webView, null)
        tabs.add(tab)

        webView.webViewClient = object : WebViewClient() {
            override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                if (url != null && view != null && view == activeTab.webView) {
                    clientActivity.onURLChanged(url)
                }
                super.doUpdateVisitedHistory(view, url, isReload)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                if (view?.isLaidOut?:false) {
                    tab.bitmap = view?.drawToBitmap()
                }
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (view != null && view == activeTab.webView) {
                    clientActivity.onProgressChanged(newProgress)
                }
            }
        }

        if (setAsActive) {
            activeTab = tab
            clientActivity.showWebView(webView)
            clientActivity.onURLChanged(activeTab.webView.url ?: "")
        }
        loadWebPage(defaultUrl)
    }

    fun closeTab(position: Int) {
        if (position >= 0 && position < tabs.size) {
            if (tabs[position] == activeTab) {
                if (tabs.size > 1) {
                    activeTab = tabs[(position + 1) % tabs.size]
                } else {
                    addTab(true)
                }
            }

            tabs.remove(tabs[position])
            clientActivity.showWebView(activeTab.webView)
        }
    }

    fun selectTab(position: Int) {
        if (position >= 0 && position < tabs.size) {
            activeTab = tabs[position]
            clientActivity.showWebView(activeTab.webView)
        }
    }

    fun goBack() {
        if (activeTab.webView.canGoBack()) {
            activeTab.webView.goBack()
        }
    }

    fun goForward() {
        if (activeTab.webView.canGoForward()) {
            activeTab.webView.goForward()
        }
    }

    fun reload() {
        activeTab.webView.reload()
    }

    fun numberOfTabs(): Int{
        return tabs.size
    }

    @Throws(UnsupportedOperationException::class) private fun buildUri(authority: String) : Uri {
        val builder = Uri.Builder()
        builder.scheme("https").authority(authority)

        return builder.build()
    }

    fun loadWebPage(text: String) {
        if(text.isNotEmpty()) {
            var urlText = text
            activeTab.webView.settings.javaScriptEnabled = true

            try {
                if (!text.startsWith("http://") && !text.startsWith("https://")) {
                    urlText = buildUri(urlText).toString()
                }
                activeTab.webView.loadUrl(urlText)
            } catch (e: UnsupportedOperationException) {
                e.printStackTrace()
            }
        }
    }

    fun updateThumbnail() {
        if (activeTab.webView?.isLaidOut?:false) {
            activeTab.bitmap = activeTab?.webView.drawToBitmap()
        }
    }

    private val clientActivity: ClientActivity = listener
    val tabs: ArrayList<Tab> = ArrayList()
    lateinit var activeTab : Tab
    val defaultUrl : String = defaultUrl
}