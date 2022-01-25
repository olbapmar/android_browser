package com.example.neevacodechallenge

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            if (findViewById<WebView>(R.id.webview).canGoBack()) {
                findViewById<WebView>(R.id.webview).goBack()
            }
        }

        findViewById<ImageView>(R.id.forwardButton).setOnClickListener {
            if (findViewById<WebView>(R.id.webview).canGoForward()) {
                findViewById<WebView>(R.id.webview).goForward()
            }
        }

        findViewById<ImageView>(R.id.reloadButton).setOnClickListener {
            findViewById<WebView>(R.id.webview).reload()
        }

        findViewById<EditText>(R.id.uriText).setOnEditorActionListener { textView, id, keyEent ->
            if (id == EditorInfo.IME_ACTION_DONE) {
                loadWebPage()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                true
            } else false
        }

        findViewById<WebView>(R.id.webview).webViewClient = object : WebViewClient() {
            override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                findViewById<EditText>(R.id.uriText).setText(url)
                super.doUpdateVisitedHistory(view, url, isReload)
            }
        }

        loadWebPage()
    }

    @Throws(UnsupportedOperationException::class)
    fun buildUri(authority: String) : Uri {
        val builder = Uri.Builder()
        builder.scheme("https").authority(authority)

        return builder.build()
    }

    fun loadWebPage() {
        var webView = findViewById<WebView>(R.id.webview)

        webView.loadUrl("")

        webView.settings.javaScriptEnabled = true

        try {
            var text = findViewById<EditText>(R.id.uriText).text.toString()
            if (!text.startsWith("http://") && !text.startsWith("https://")) {
                text = buildUri(text).toString()
            }
            webView.loadUrl(text)
        } catch (e: UnsupportedOperationException) {
            e.printStackTrace()
        }
    }
}