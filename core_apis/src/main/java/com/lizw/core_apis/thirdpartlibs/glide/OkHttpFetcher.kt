package com.lizw.core_apis.thirdpartlibs.glide

import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.util.ContentLengthInputStream
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.io.InputStream
import kotlin.concurrent.Volatile

/**
 * 实现带进度的Glide图片加载功能
 * 1、替换 Glide 通讯组件
 */
class OkHttpFetcher(client: OkHttpClient, url: GlideUrl) : DataFetcher<InputStream> {
    private val client: OkHttpClient
    private val url: GlideUrl
    private var stream: InputStream? = null
    private var responseBody: ResponseBody? = null

    @Volatile
    private var isCancelled = false

    init {
        this.client = client
        this.url = url
    }

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
        val request: Request = Request.Builder().run {
            url(url.toStringUrl())
            for ((key, value) in url.headers) {
                addHeader(key, value)
            }
            build()
        }
        if (isCancelled) {
            return
        }
        val response: Response = client.newCall(request).execute()
        responseBody = response.body
        if (!response.isSuccessful || responseBody == null) {
            throw IOException("Request failed with code: " + response.code)
        }
        stream = ContentLengthInputStream.obtain(
            responseBody!!.byteStream(),
            responseBody!!.contentLength()
        )
    }

    override fun cleanup() {
        try {
            if (stream != null) {
                stream!!.close()
            }
            if (responseBody != null) {
                responseBody!!.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getId(): String {
        return url.cacheKey
    }

    override fun cancel() {
        isCancelled = true
    }

    override fun getDataClass(): Class<InputStream> {
        return InputStream::class.java
    }

    override fun getDataSource(): DataSource {
        return DataSource.REMOTE
    }
}