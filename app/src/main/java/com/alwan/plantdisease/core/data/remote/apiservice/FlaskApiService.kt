package com.alwan.plantdisease.core.data.remote.apiservice

import com.alwan.plantdisease.core.data.remote.response.flask.PlantDiseaseResponse
import com.alwan.plantdisease.core.data.remote.response.flask.CornFeaturesResponse
import com.alwan.plantdisease.core.data.remote.response.flask.PotatoFeaturesResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FlaskApiService {
    @Multipart
    @POST("classify-potato")
    suspend fun classifyPotato(@Part image: MultipartBody.Part): Response<PlantDiseaseResponse<PotatoFeaturesResponse>>

    @Multipart
    @POST("classify-corn")
    suspend fun classifyCorn(@Part image: MultipartBody.Part): Response<PlantDiseaseResponse<CornFeaturesResponse>>
}