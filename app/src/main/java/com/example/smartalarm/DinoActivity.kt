package com.example.smartalarm

import android.annotation.SuppressLint
import android.os.Bundle
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
        webView.loadUrl(url)
    }
}