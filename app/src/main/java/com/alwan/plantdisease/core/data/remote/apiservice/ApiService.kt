package com.alwan.plantdisease.core.data.remote.apiservice

import com.alwan.plantdisease.core.data.remote.response.weather.WeatherResponse
import com.alwan.plantdisease.util.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun getWeatherInfo(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = Constant.WEATHER_API_KEY
    ): Response<WeatherResponse>
}