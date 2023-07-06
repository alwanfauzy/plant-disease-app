package com.alwan.plantdisease.core.domain.repository

import com.alwan.plantdisease.core.data.util.Resource
import com.alwan.plantdisease.core.domain.entity.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeather(lat: Double, long: Double): Flow<Resource<WeatherInfo>>
}