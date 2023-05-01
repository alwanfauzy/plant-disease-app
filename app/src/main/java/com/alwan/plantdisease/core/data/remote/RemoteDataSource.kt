package com.alwan.plantdisease.core.data.remote

import android.util.Log
import com.alwan.plantdisease.core.data.remote.network.ApiService
import com.alwan.plantdisease.core.data.remote.network.NetworkResult
import com.alwan.plantdisease.core.data.remote.response.weather.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getWeather(lat: Double, long: Double): Flow<NetworkResult<WeatherResponse>> =
        handleApi { apiService.getWeatherInfo(lat, long) }

}

fun <T : Any> NetworkResult<T>.toFlow() = flow {
    try {
        emit(this@toFlow)
    } catch (e: Exception) {
        Log.e(RemoteDataSource::class.java.name, e.toString())
        emit(NetworkResult.Exception(e))
    }
}.flowOn(Dispatchers.IO)

suspend fun <T : Any> handleApi(
    execute: suspend () -> Response<T>
): Flow<NetworkResult<T>> = flow {
    try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            emit(NetworkResult.Success(body))
        } else {
            emit(NetworkResult.Error(code = response.code(), message = response.message()))
        }
    } catch (e: HttpException) {
        emit(NetworkResult.Error(code = e.code(), message = e.message()))
    } catch (e: Throwable) {
        emit(NetworkResult.Exception(e))
    }
}.flowOn(Dispatchers.IO)
