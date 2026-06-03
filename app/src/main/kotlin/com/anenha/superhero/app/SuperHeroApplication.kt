package com.anenha.superhero.app

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.anenha.superhero.core.network.interceptor.CloudflareInterceptor
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient

@HiltAndroidApp
class SuperHeroApplication : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        Coil.setImageLoader(this)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .okHttpClient {
                OkHttpClient.Builder()
                    .addInterceptor(CloudflareInterceptor(this))
                    .build()
            }
            .crossfade(true)
            .build()
    }
}