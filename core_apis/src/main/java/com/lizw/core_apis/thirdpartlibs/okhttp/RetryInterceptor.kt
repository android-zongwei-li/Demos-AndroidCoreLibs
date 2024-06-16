package com.lizw.core_apis.thirdpartlibs.okhttp

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * 重试拦截器
 */
class RetryInterceptor(
        //最大重试次数
        private var maxRetry: Int,
) : Interceptor {
    companion object{
        private const val TAG = "RetryInterceptor"
    }
    // 假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）
    private var retryNum = 0
    
    @Throws(IOException::class)
    override fun intercept(chain: Chain): Response {
        val request: Request = chain.request()
        Log.d(TAG, "intercept: retryNum= $retryNum")
        var response: Response = chain.proceed(request)
        while (!response.isSuccessful && retryNum < maxRetry) {
            retryNum++
            Log.d(TAG, "intercept: retryNum= $retryNum")
            response = chain.proceed(request)
        }
        return response
    }
}
