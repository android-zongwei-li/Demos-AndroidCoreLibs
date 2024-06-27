package com.lizw.core_apis.thirdpartlibs.glide

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.InputStream
import java.util.concurrent.TimeUnit

private const val TAG = "GlideOkHttpModule"

/**
 * 作用：Glide 使用 Okhttp 请求网络。
 */
@GlideModule
class GlideOkHttpModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        Log.i(TAG, "registerComponents: ")
        val client: OkHttpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(ProgressInterceptor())
            .connectTimeout(6, TimeUnit.SECONDS)
            .build()

        registry.replace(
            GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(client)
        )
    }
}