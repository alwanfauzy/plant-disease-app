package com.alwan.plantdisease.core.domain.usecase

import com.alwan.plantdisease.core.data.MainRepositoryImpl
import com.alwan.plantdisease.core.data.Resource
import com.alwan.plantdisease.core.domain.entity.WeatherInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherInteractor @Inject constructor(private val mainRepository: MainRepositoryImpl) :
    WeatherUseCase {
    override fun getWeather(lat: Double, long: Double): Flow<Resource<WeatherInfo>> =
        mainRepository.getWeather(lat, long)
}