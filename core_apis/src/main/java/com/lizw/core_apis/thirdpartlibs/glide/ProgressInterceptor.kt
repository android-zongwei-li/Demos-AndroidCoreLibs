package com.lizw.core_apis.thirdpartlibs.glide

import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException


/**
 *
 */
class ProgressInterceptor : Interceptor {
    companion object {
        val progressListeners: MutableMap<String, ProgressListener> = HashMap()
        fun addListener(url: String, listener: ProgressListener) {
            progressListeners[url] = listener
        }

        fun removeListener(url: String) {
            progressListeners.remove(url)
        }
    }

    @Throws(IOException::class)
    override fun intercept(chain: Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        val body: ResponseBody = response.body ?: return response

        val url = request.url.toString()
        return response.newBuilder().body(ProgressResponseBody(url, body)).build()
    }
}

interface ProgressListener {
    fun onProgress(progress: Int)
}