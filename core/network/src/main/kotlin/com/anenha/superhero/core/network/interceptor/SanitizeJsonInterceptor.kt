package com.anenha.superhero.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * Interceptor that cleans up "dirty" or double-escaped JSON from the API.
 * It fixes escaped forward slashes and double-escaped quotes globally.
 */
class SanitizeJsonInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val body = response.body
        val contentType = body?.contentType()

        // Only process successful JSON responses
        if (response.isSuccessful && body != null && contentType?.subtype?.contains("json") == true) {
            val rawContent = body.string()

            val sanitized = rawContent
                // Fixes slashes: "\/" -> "/" (Safe in JSON body)
                .replace("\\/", "/")
                // Fixes double-escaped quotes: "\\\"" -> "\""
                // This ensures the JSON parser sees a standard escaped quote and unescapes it correctly.
                .replace("\\\\\"", "\\\"")

            return response.newBuilder()
                .body(sanitized.toResponseBody(contentType))
                .build()
        }
        return response
    }
}