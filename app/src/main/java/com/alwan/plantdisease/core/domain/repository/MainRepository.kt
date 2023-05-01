package com.alwan.plantdisease.core.domain.repository

import com.alwan.plantdisease.core.data.Resource
import com.alwan.plantdisease.core.domain.entity.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getWeather(lat: Double, long: Double): Flow<Resource<WeatherInfo>>
}