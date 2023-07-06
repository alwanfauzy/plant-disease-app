package com.alwan.plantdisease.core.data.repository

import com.alwan.plantdisease.core.data.util.NetworkOnlyResource
import com.alwan.plantdisease.core.data.util.Resource
import com.alwan.plantdisease.core.data.mapper.mapToDomain
import com.alwan.plantdisease.core.data.remote.datasource.WeatherRemoteDataSource
import com.alwan.plantdisease.core.data.util.NetworkResult
import com.alwan.plantdisease.core.data.remote.response.weather.WeatherResponse
import com.alwan.plantdisease.core.domain.entity.weather.WeatherInfo
import com.alwan.plantdisease.core.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherRemoteDataSource: WeatherRemoteDataSource) :
    WeatherRepository {
    override fun getWeather(lat: Double, long: Double): Flow<Resource<WeatherInfo>> =
        object : NetworkOnlyResource<WeatherInfo, WeatherResponse>() {
            override suspend fun loadFromNetwork(data: WeatherResponse): Flow<WeatherInfo> =
                data.mapToDomain()

            override suspend fun createCall(): Flow<NetworkResult<WeatherResponse>> =
                weatherRemoteDataSource.getWeather(lat, long)

        }.asFlow()

}