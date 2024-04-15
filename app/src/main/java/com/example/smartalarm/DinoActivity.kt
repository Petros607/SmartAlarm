package com.example.smartalarm

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import androidx.activity.ComponentActivity


class DinoActivity : ComponentActivity() {
    private lateinit var webView : WebView
    private val url = "file:///android_asset/index.html"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dino)
        webView = findViewById(R.id.wv)
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(WebAppInterface(this), "Android");
        webView.loadUrl(url)
    }

    class WebAppInterface internal constructor(c: Context) {
        var mContext: Context

        init {
            mContext = c
        }

        @JavascriptInterface
        fun sendData(data: String?) {
            Log.d("WebViewData", data!!)
            var value = data?.toIntOrNull()
            if (value != null && value >= 300) {
                (mContext as DinoActivity).finish()
            }
        }
    }
}