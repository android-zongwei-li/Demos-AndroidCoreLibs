package com.lizw.core_apis.thirdpartlibs.retrofit.basicuse

import com.lizw.core_apis.thirdpartlibs.retrofit.beans.WeatherInfo
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 作用：聚合数据 Api 接口。
 * https://www.juhe.cn/docs
 * 技术栈：
 */
interface JuheDataApi {
    // val BASE_URL_JU_HE: String =
    
    /**
     * @param city 如：上海、南京等
     * @param key 聚合天气接口需要的key，后台查看
     */
    @GET(value = "simpleWeather/query")
    fun getWeatherBy(@Query(value = "city") city: String, @Query(value = "key") key: String = "801bd3f8a310ab6e3e5a83f6e537905b"): WeatherInfo
}