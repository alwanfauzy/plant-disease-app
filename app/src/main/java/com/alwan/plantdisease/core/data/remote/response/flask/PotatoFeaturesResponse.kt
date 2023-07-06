package com.alwan.plantdisease.core.data.remote.response.flask

import com.google.gson.annotations.SerializedName

data class PotatoFeaturesResponse(
    @SerializedName("early_blight")
    val earlyBlight: Float?,
    @SerializedName("healthy")
    val healthy: Float?,
    @SerializedName("late_blight")
    val lateBlight: Float?,
)
