package com.alwan.plantdisease.core.data.mapper

import com.alwan.plantdisease.core.data.remote.response.flask.CornFeaturesResponse
import com.alwan.plantdisease.core.data.remote.response.flask.PlantDiseaseResponse
import com.alwan.plantdisease.core.domain.entity.flask.CornDisease
import com.alwan.plantdisease.util.orZero
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class CornDiseaseMapper @Inject constructor(){
    fun mapToEntity(from: PlantDiseaseResponse<CornFeaturesResponse>) = flowOf(
        CornDisease(
            diseasePredicted = from.data?.featurePredicted.orEmpty(),
            blight = from.data?.features?.blight.orZero(),
            commonRust = from.data?.features?.commonRust.orZero(),
            grayLeafSpot = from.data?.features?.grayLeafSpot.orZero(),
            healthy = from.data?.features?.healthy.orZero(),
        )
    )
}