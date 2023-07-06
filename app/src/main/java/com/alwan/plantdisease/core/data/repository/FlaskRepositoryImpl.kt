package com.alwan.plantdisease.core.data.repository

import com.alwan.plantdisease.core.data.util.NetworkOnlyResource
import com.alwan.plantdisease.core.data.util.Resource
import com.alwan.plantdisease.core.data.mapper.CornDiseaseMapper
import com.alwan.plantdisease.core.data.mapper.PotatoDiseaseMapper
import com.alwan.plantdisease.core.data.remote.datasource.FlaskRemoteDataSource
import com.alwan.plantdisease.core.data.util.NetworkResult
import com.alwan.plantdisease.core.data.remote.response.flask.CornFeaturesResponse
import com.alwan.plantdisease.core.data.remote.response.flask.PlantDiseaseResponse
import com.alwan.plantdisease.core.data.remote.response.flask.PotatoFeaturesResponse
import com.alwan.plantdisease.core.domain.entity.flask.CornDisease
import com.alwan.plantdisease.core.domain.entity.flask.PotatoDisease
import com.alwan.plantdisease.core.domain.repository.FlaskRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class FlaskRepositoryImpl @Inject constructor(
    private val flaskRemoteDataSource: FlaskRemoteDataSource,
    private val potatoDiseaseMapper: PotatoDiseaseMapper,
    private val cornDiseaseMapper: CornDiseaseMapper,
) : FlaskRepository {
    override fun classifyPotato(image: MultipartBody.Part): Flow<Resource<PotatoDisease>> = object :
        NetworkOnlyResource<PotatoDisease, PlantDiseaseResponse<PotatoFeaturesResponse>>() {
        override suspend fun loadFromNetwork(data: PlantDiseaseResponse<PotatoFeaturesResponse>): Flow<PotatoDisease> =
            potatoDiseaseMapper.mapToEntity(data)

        override suspend fun createCall(): Flow<NetworkResult<PlantDiseaseResponse<PotatoFeaturesResponse>>> =
            flaskRemoteDataSource.classifyPotato(image)

    }.asFlow()

    override fun classifyCorn(image: MultipartBody.Part): Flow<Resource<CornDisease>> =
        object : NetworkOnlyResource<CornDisease, PlantDiseaseResponse<CornFeaturesResponse>>() {
            override suspend fun loadFromNetwork(data: PlantDiseaseResponse<CornFeaturesResponse>): Flow<CornDisease> =
                cornDiseaseMapper.mapToEntity(data)

            override suspend fun createCall(): Flow<NetworkResult<PlantDiseaseResponse<CornFeaturesResponse>>> =
                flaskRemoteDataSource.classifyCorn(image)

        }.asFlow()
}