package com.lizw.core_apis.thirdpartlibs.retrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lizw.core_apis.databinding.ActivityRetrofitBinding
import com.lizw.core_apis.thirdpartlibs.retrofit.basicuse.BaiduService
import com.lizw.core_apis.thirdpartlibs.retrofit.basicuse.JuheDataApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RetrofitActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityRetrofitBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        
        // testBaidu()
        
        viewBinding.btnGetWeather.setOnClickListener {
            Retrofit.Builder()
                    .baseUrl("http://apis.juhe.cn/")
                    // .addConverterFactory(Gson)
                    .build()
                    .create(JuheDataApi::class.java)
                    .getWeatherBy("上海").apply {
                        Log.i(TAG, this.toString())
                        
                        viewBinding.tvWeatherInfo.text = this.toString()
                    }
        }
    }
    
    fun testBaidu() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.baidu.com/")
                .build()
        
        val service = retrofit.create(BaiduService::class.java)
        val repos: Call<ResponseBody> = service.listRepos()
        repos.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.i(TAG, response.toString())
                Log.i(TAG, response.body()!!.source().toString())
            }
            
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.i(TAG, t.toString())
            }
        })
    }
}