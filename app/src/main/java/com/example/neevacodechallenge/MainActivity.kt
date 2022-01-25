package com.example.neevacodechallenge

import android.content.Context
import android.graphics.ColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar

class MainActivity : AppCompatActivity(), TabsManager.ClientActivity {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabsManager = TabsManager(this, getString(R.string.uri_default))
        tabsManager.addTab(true)


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
            var dialog = TabsDialogFragment(tabsManager)
            dialog.show(supportFragmentManager, "TabDialog")
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

    override fun createWebView(): WebView {
        return WebView(this)
    }

    override fun showWebView(webView: WebView) {
        findViewById<FrameLayout>(R.id.webViewHolder).removeAllViews()
        findViewById<FrameLayout>(R.id.webViewHolder).addView(webView)
        findViewById<EditText>(R.id.uriText).setText(webView.url ?: "")
    }

    override fun onProgressChanged(progress: Int) {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        if(progress == 100) {
            progressBar.visibility = GONE
        } else {
            progressBar.progress = progress
            progressBar.visibility = VISIBLE
        }
    }

    override fun onBackPressed() {
        if (tabsManager.numberOfTabs() > 1) tabsManager.goBack()
    }
}