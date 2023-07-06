package com.alwan.plantdisease.core.data.remote.datasource

import com.alwan.plantdisease.core.data.remote.apiservice.ApiService
import com.alwan.plantdisease.core.data.util.NetworkResult
import com.alwan.plantdisease.core.data.remote.response.weather.WeatherResponse
import com.alwan.plantdisease.core.data.util.handleApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getWeather(lat: Double, long: Double): Flow<NetworkResult<WeatherResponse>> =
        handleApi { apiService.getWeatherInfo(lat, long) }

}