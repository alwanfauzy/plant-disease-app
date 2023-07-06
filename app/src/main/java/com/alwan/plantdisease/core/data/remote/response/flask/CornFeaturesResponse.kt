package com.alwan.plantdisease.core.data.remote.response.flask

import com.google.gson.annotations.SerializedName

data class CornFeaturesResponse(
    @SerializedName("blight")
    val blight: Float?,
    @SerializedName("common_rust")
    val commonRust: Float?,
    @SerializedName("gray_leaf_spot")
    val grayLeafSpot: Float?,
    @SerializedName("healthy")
    val healthy: Float?,
)
