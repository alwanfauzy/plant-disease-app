package com.alwan.plantdisease.core.domain.usecase

import com.alwan.plantdisease.core.data.repository.WeatherRepositoryImpl
import com.alwan.plantdisease.core.data.util.Resource
import com.alwan.plantdisease.core.domain.entity.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherInteractor @Inject constructor(private val mainRepository: WeatherRepositoryImpl) :
    WeatherUseCase {
    override fun getWeather(lat: Double, long: Double): Flow<Resource<WeatherInfo>> =
        mainRepository.getWeather(lat, long)
}