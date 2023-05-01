package com.alwan.plantdisease.core.data

import com.alwan.plantdisease.core.data.remote.RemoteDataSource
import com.alwan.plantdisease.core.data.remote.network.NetworkResult
import com.alwan.plantdisease.core.data.remote.response.weather.WeatherResponse
import com.alwan.plantdisease.core.domain.entity.WeatherInfo
import com.alwan.plantdisease.core.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    MainRepository {
    override fun getWeather(lat: Double, long: Double): Flow<Resource<WeatherInfo>> =
        object : NetworkOnlyResource<WeatherInfo, WeatherResponse>() {
            override suspend fun loadFromNetwork(data: WeatherResponse): Flow<WeatherInfo> =
                data.mapToDomain()

            override suspend fun createCall(): Flow<NetworkResult<WeatherResponse>> =
                remoteDataSource.getWeather(lat, long)

        }.asFlow()

}