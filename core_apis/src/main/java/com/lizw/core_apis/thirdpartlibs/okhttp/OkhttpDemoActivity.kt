package com.lizw.core_apis.thirdpartlibs.okhttp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.lizw.core_apis.databinding.ActivityOkhttpDemoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.Interceptor.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

class OkhttpDemoActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "OkhttpDemoActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityOkhttpDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnAsyncGet.setOnClickListener {
                asyncGet()
            }
            btnGet.setOnClickListener {
                syncGet()
            }
            btnPostString.setOnClickListener {
                postString()
            }
            btnPostKeyValue.setOnClickListener {
                postKeyValue("userName", "password")
            }
            btnPostJson.setOnClickListener {
                postJson("{\"x\":s}")
            }
            btnRetry.setOnClickListener {
                RetryTest().test()
            }
        }
    }

    fun asyncGet() {
        val url = "http://wwww.baidu.com"
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ParamsInterceptor())
            .build()
        val request: Request = Request.Builder()
            .url(url)
            .get() //默认就是GET请求，可以省略
            .build()
        val call: Call = okHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: ")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                //response.body().string() 获得服务器返回的数据
                Log.d(TAG, "onResponse: " + response.body?.string())
            }
        })
    }

    fun syncGet() {
        val request: Request = Request.Builder().url("http://wwww.baidu.com").build()
        val call = OkHttpClient().apply {
        }.newCall(request)
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = call.execute()
                Log.d(TAG, "run: " + response.body?.string())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun postString() {
        val url = "http://wwww.baidu.com"
        val client = OkHttpClient()
        val mediaType = "text/x-markdown; charset=utf-8".toMediaTypeOrNull()
        val requestBody = RequestBody.create(mediaType, "RequestBody")
        val request: Request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: ")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "onResponse: " + response.body?.string())
            }
        })
    }

    fun postKeyValue(userName: String, password: String) {
        val url = "http://wwww.baidu.com"
        val client = OkHttpClient()
        val requestBody: RequestBody = FormBody.Builder()
            .add("username", userName)
            .add("password", password)
            .build()
        val request: Request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: ")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "onResponse: " + response.body?.string())
            }
        })
    }

    fun postJson(jsonData: String) {
        val url = "http://wwww.baidu.com"
        val client = OkHttpClient()
        val mediaType = "application/json;charset=utf-8".toMediaTypeOrNull()
        val requestBody: RequestBody = RequestBody.create(mediaType, jsonData)
        val request: Request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: ")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "onResponse: " + response.body?.string())
            }
        })
    }

    fun testCustomDns() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .dns(CustomNDS(3000))
            .build()
    }
}