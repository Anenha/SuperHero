package com.anenha.superhero.core.network.interceptor

import android.content.Context
import android.content.Intent
import android.util.Log
import com.anenha.superhero.core.network.ChallengeActivity
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

/**
 * Interceptor that detects Cloudflare's "Managed Challenge" and attempts to solve it
 * by showing an interactive WebView to the user to obtain a valid `cf_clearance` cookie.
 */
class CloudflareInterceptor(private val context: Context) : Interceptor {

    companion object {
        @Volatile
        var clearanceCookie: String? = null
        val latch = AtomicReference<CountDownLatch?>(null)
        private val lock = Any()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Skip for non-superherodb requests
        if (!originalRequest.url.host.contains("superherodb.com")) {
            return chain.proceed(originalRequest)
        }

        val browserUA = "Mozilla/5.0 (Linux; Android) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Mobile Safari/537.36"

        fun buildRequest(cookie: String?) = originalRequest.newBuilder()
            .header("User-Agent", browserUA)
            .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8")
            .header("Accept-Language", "en-US,en;q=0.9")
            .apply { if (cookie != null) {
                Log.d("CloudflareInterceptor", "Applying cookie: $cookie")
                header("Cookie", cookie)
            } }
            .build()

        var response = chain.proceed(buildRequest(clearanceCookie))

        if (response.code == 403 && (response.header("cf-mitigated") == "challenge" || response.header("server") == "cloudflare")) {
            Log.d("CloudflareInterceptor", "Detected Cloudflare challenge for ${originalRequest.url}")
            response.close()

            synchronized(lock) {
                if (clearanceCookie == null) {
                    if (latch.get() == null) {
                        Log.d("CloudflareInterceptor", "Starting ChallengeActivity...")
                        latch.set(CountDownLatch(1))
                        val intent = Intent(context, ChallengeActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            putExtra("url", "https://www.superherodb.com/")
                            putExtra("ua", browserUA)
                        }
                        context.startActivity(intent)
                    }
                }
            }

            // Wait up to 1 minute for the user to solve the challenge
            Log.d("CloudflareInterceptor", "Waiting for challenge to be solved...")
            latch.get()?.await(1, TimeUnit.MINUTES)

            if (clearanceCookie != null) {
                Log.d("CloudflareInterceptor", "Retrying request with solved cookie")
                // Reset latch for future challenges if needed (though usually session lasts)
                latch.set(null)
                return chain.proceed(buildRequest(clearanceCookie))
            } else {
                Log.e("CloudflareInterceptor", "Failed to solve challenge (timeout or closed)")
                latch.set(null)
            }
        }

        return response
    }
}