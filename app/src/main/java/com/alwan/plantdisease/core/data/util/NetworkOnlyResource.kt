package com.alwan.plantdisease.core.data.util

import kotlinx.coroutines.flow.*

abstract class NetworkOnlyResource<ResultType, RequestType : Any> {

    private val result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())

        val apiResponse = createCall().first()
        apiResponse.onSuccess { data ->
            emitAll(loadFromNetwork(data).map {
                Resource.Success(it)
            })
        }.onError { code, message ->
            val errorMessage = "$message ($code)"
            emit(Resource.Error(errorMessage))
        }.onException { e ->
            emit(Resource.Error(e.message))
        }
    }

    protected abstract suspend fun loadFromNetwork(data: RequestType): Flow<ResultType>

    protected abstract suspend fun createCall(): Flow<NetworkResult<RequestType>>

    fun asFlow(): Flow<Resource<ResultType>> = result
}