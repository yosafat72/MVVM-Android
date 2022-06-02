package com.example.mvvmflow.view

import android.annotation.SuppressLint
import android.net.http.SslError
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.mvvmflow.R
import com.example.mvvmflow.databinding.ActivityDetailNewBinding

class DetailNewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailNewBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val link = intent.getStringExtra("link").toString()

        if(link.isEmpty() || link == "null"){
            binding.imgNotFound.visibility = View.VISIBLE
            binding.webView.visibility = View.GONE
        }else{
            binding.imgNotFound.visibility = View.GONE
            binding.webView.visibility = View.VISIBLE

            binding.webView.settings.javaScriptEnabled = true
            binding.webView.loadUrl(link)

        }

    }
}