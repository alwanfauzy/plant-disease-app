package com.alwan.plantdisease.core.data.remote.response.flask

import com.google.gson.annotations.SerializedName

data class PlantDiseaseResponse<T>(
    val message: String?,
    val data: PlantDiseaseDataResponse<T>?,
)

data class PlantDiseaseDataResponse<T>(
    @SerializedName("feature_predicted")
    val featurePredicted: String?,
    val features: T?,
)