package com.anenha.superhero.core.network

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import android.util.Log
import com.anenha.superhero.core.network.interceptor.CloudflareInterceptor

class ChallengeActivity : ComponentActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val url = intent.getStringExtra("url") ?: "https://www.superherodb.com/"
        val userAgent = intent.getStringExtra("ua")

        val webView = WebView(this)
        setContentView(webView)

        title = getString(R.string.cloudflare_verification_title)

        // Set layout rules for the dialog window
        val displayMetrics = resources.displayMetrics
        val width = (displayMetrics.widthPixels * 0.90).toInt() // 90% of screen width
        val height = (displayMetrics.heightPixels * 0.60).toInt() // 60% of screen height
        window.setLayout(width, height)
        window.setGravity(android.view.Gravity.CENTER)

        // Prevent dismissal if user clicks outside the dialog area
        setFinishOnTouchOutside(false)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        if (userAgent != null) {
            webView.settings.userAgentString = userAgent
        }

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                val cookies = CookieManager.getInstance().getCookie(url)
                Log.d("ChallengeActivity", "Page finished: $url, Cookies: $cookies")
                if (cookies?.contains("cf_clearance") == true) {
                    Log.d("ChallengeActivity", "Success! Found cf_clearance cookie.")
                    CloudflareInterceptor.clearanceCookie = cookies
                    CloudflareInterceptor.latch.get()?.countDown()
                    finish()
                }
            }
        }

        if (savedInstanceState == null) {
            // Clear cookies to ensure we don't pick up an expired one from previous sessions
            Log.d("ChallengeActivity", "First launch, clearing cookies before loading challenge...")
            CookieManager.getInstance().removeAllCookies {
                Log.d("ChallengeActivity", "Cookies cleared, loading URL: $url")
                webView.loadUrl(url)
            }
        } else {
            Log.d("ChallengeActivity", "Re-created instance, loading URL without clearing: $url")
            webView.loadUrl(url)
        }
    }

    override fun onDestroy() {
        // Ensure latch is released if user closes the activity without solving
        // We only count down if the activity is actually finishing, not during config changes like rotation
        if (isFinishing) {
            Log.d("ChallengeActivity", "Activity finishing, releasing latch if still present")
            CloudflareInterceptor.latch.get()?.countDown()
        }
        super.onDestroy()
    }
}
