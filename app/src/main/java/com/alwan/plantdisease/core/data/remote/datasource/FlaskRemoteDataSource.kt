package com.alwan.plantdisease.core.data.remote.datasource

import com.alwan.plantdisease.core.data.remote.apiservice.FlaskApiService
import com.alwan.plantdisease.core.data.util.NetworkResult
import com.alwan.plantdisease.core.data.remote.response.flask.CornFeaturesResponse
import com.alwan.plantdisease.core.data.remote.response.flask.PlantDiseaseResponse
import com.alwan.plantdisease.core.data.remote.response.flask.PotatoFeaturesResponse
import com.alwan.plantdisease.core.data.util.handleApi
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class FlaskRemoteDataSource @Inject constructor(private val flaskApiService: FlaskApiService) {

    suspend fun classifyPotato(image: MultipartBody.Part): Flow<NetworkResult<PlantDiseaseResponse<PotatoFeaturesResponse>>> =
        handleApi { flaskApiService.classifyPotato(image) }

    suspend fun classifyCorn(image: MultipartBody.Part): Flow<NetworkResult<PlantDiseaseResponse<CornFeaturesResponse>>> =
        handleApi { flaskApiService.classifyCorn(image) }
}