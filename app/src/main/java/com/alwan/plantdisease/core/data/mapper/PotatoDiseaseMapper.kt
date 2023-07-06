package com.alwan.plantdisease.core.data.mapper

import com.alwan.plantdisease.core.data.remote.response.flask.PlantDiseaseResponse
import com.alwan.plantdisease.core.data.remote.response.flask.PotatoFeaturesResponse
import com.alwan.plantdisease.core.domain.entity.flask.PotatoDisease
import com.alwan.plantdisease.util.orZero
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class PotatoDiseaseMapper @Inject constructor() {
    fun mapToEntity(from: PlantDiseaseResponse<PotatoFeaturesResponse>) = flowOf(
        PotatoDisease(
            diseasePredicted = from.data?.featurePredicted.orEmpty(),
            earlyBlight = from.data?.features?.earlyBlight.orZero(),
            healthy = from.data?.features?.healthy.orZero(),
            lateBlight = from.data?.features?.lateBlight.orZero()
        )
    )
}