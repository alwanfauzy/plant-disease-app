package com.alwan.plantdisease.core.domain.usecase

import com.alwan.plantdisease.core.data.util.Resource
import com.alwan.plantdisease.core.domain.entity.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherUseCase {
    fun getWeather(lat: Double, long: Double): Flow<Resource<WeatherInfo>>
}