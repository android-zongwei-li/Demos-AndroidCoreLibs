package com.lizw.core_apis.thirdpartlibs.glide

import android.util.Log
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.Source
import okio.buffer
import java.io.IOException

/**
 *
 * @param url 图片的url地址
 * @param responseBody OkHttp拦截到的原始的ResponseBody对象
 */
class ProgressResponseBody(url: String, responseBody: ResponseBody) : ResponseBody() {
    companion object {
        private const val TAG = "ProgressResponseBody"
    }

    private var bufferedSource: BufferedSource? = null
    private val responseBody: ResponseBody
    private var progressListener: ProgressListener?

    init {
        this.responseBody = responseBody
        progressListener = ProgressInterceptor.progressListeners[url]
    }

    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun source(): BufferedSource {
        if (bufferedSource == null) {
            bufferedSource = ProgressSource(responseBody.source()).buffer()
        }
        return bufferedSource!!
    }

    /**
     * ForwardingSource是一个使用委托模式的类，它不处理任何具体的逻辑，只是负责将传入的原始Source对象进行中转。
     * 使用ProgressSource继承自ForwardingSource，就可以在中转的过程中加入自己的逻辑了。
     */
    private inner class ProgressSource(source: Source) : ForwardingSource(source) {
        var totalBytesRead: Long = 0
        var currentProgress = 0

        @Throws(IOException::class)
        override fun read(sink: Buffer, byteCount: Long): Long {
            val bytesRead = super.read(sink, byteCount)
            val fullLength = responseBody.contentLength()
            if (bytesRead == -1L) {
                totalBytesRead = fullLength
            } else {
                totalBytesRead += bytesRead
            }
            val progress = (100f * totalBytesRead / fullLength).toInt()
            Log.d(TAG, "download progress is $progress")
            if (progressListener != null && progress != currentProgress) {
                progressListener!!.onProgress(progress)
            }
            if (progressListener != null && totalBytesRead == fullLength) {
                progressListener = null
            }
            currentProgress = progress
            return bytesRead
        }
    }
}