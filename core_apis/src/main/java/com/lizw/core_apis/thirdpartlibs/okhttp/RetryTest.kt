package com.lizw.core_apis.thirdpartlibs.okhttp

import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

/**
 * 测试自定义重试拦截器
 */
class RetryTest {
    companion object {
        private const val TAG = "RetryTest"
    }
    
    private var testUrl = "https://www.baidu.com/"
    
    @Throws(IOException::class)
    fun test() {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val mClient = OkHttpClient.Builder()
                .addInterceptor(RetryInterceptor(2)) //重试
                // .addInterceptor(logging) //网络日志
                .addInterceptor(TestInterceptor()) //模拟网络请求
                .build()
        
        val request: Request = Request.Builder()
                .url(testUrl)
                .build()
        val response = mClient.newCall(request).execute()
        println("onResponse:" + response.body!!.string())
    }
    
    internal inner class TestInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val url = request.url.toString()
            println("url=$url")
            val response: Response = if (url == testUrl) {
                // 模拟的错误的返回值
                val responseString = "{\"message\":\"我是模拟的数据\"}"
                val responseBody = responseString.toByteArray().toResponseBody("application/json".toMediaTypeOrNull())
                Response.Builder()
                        .code(400)
                        .request(request)
                        .protocol(Protocol.HTTP_1_0)
                        .message("测试：无响应")
                        .body(responseBody)
                        .addHeader("content-type", "application/json")
                        .build()
            } else {
                chain.proceed(request)
            }
            return response
        }
    }
}