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
import android.widget.FrameLayout
import android.widget.ImageView

class MainActivity : AppCompatActivity(), TabsManager.URLChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView = WebView(this)

        findViewById<FrameLayout>(R.id.webViewHolder).addView(webView)

        tabsManager = TabsManager(webView, this)


        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            tabsManager.goBack()
        }

        findViewById<ImageView>(R.id.forwardButton).setOnClickListener {
            tabsManager.goForward()
        }

        findViewById<ImageView>(R.id.reloadButton).setOnClickListener {
            tabsManager.reload()
        }

        findViewById<ImageView>(R.id.tabsButton).setOnClickListener {
            val newWebView = WebView(this)
            findViewById<FrameLayout>(R.id.webViewHolder).removeAllViews()
            findViewById<FrameLayout>(R.id.webViewHolder).addView(newWebView)
            tabsManager.addTab(newWebView, true)
            tabsManager.loadWebPage(getString(R.string.uri_default))
        }

        findViewById<EditText>(R.id.uriText).setOnEditorActionListener { textView, id, keyEent ->
            if (id == EditorInfo.IME_ACTION_DONE) {
                tabsManager.loadWebPage(findViewById<EditText>(R.id.uriText).text.toString())
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                true
            } else false
        }

        tabsManager.loadWebPage(findViewById<EditText>(R.id.uriText).text.toString())
    }

    lateinit var tabsManager: TabsManager

    override fun onURLChanged(newURL: String) {
        findViewById<EditText>(R.id.uriText).setText(newURL)
    }
}