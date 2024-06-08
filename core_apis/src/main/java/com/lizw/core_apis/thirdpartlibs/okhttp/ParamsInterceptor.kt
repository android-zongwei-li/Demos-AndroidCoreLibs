package com.lizw.core_apis.thirdpartlibs.okhttp

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

/**
 */
class ParamsInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d("custom intercept", request.toString())
        
        val response = chain.proceed(request)
        Log.d("custom intercept", response.toString())
        return response
    }
}