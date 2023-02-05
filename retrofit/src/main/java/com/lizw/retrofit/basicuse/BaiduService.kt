package com.lizw.retrofit.basicuse

import okhttp3.ResponseBody
import retrofit2.http.GET

/**
 *
 * author: zongwei.li created on: 2023/2/5
 */
interface BaiduService {
    @GET("https://www.baidu.com/")
    fun listRepos(): retrofit2.Call<ResponseBody>
}