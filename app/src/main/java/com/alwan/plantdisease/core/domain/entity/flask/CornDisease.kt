package com.alwan.plantdisease.core.domain.entity.flask

data class CornDisease(
    val diseasePredicted: String,
    val blight: Float,
    val commonRust: Float,
    val grayLeafSpot: Float,
    val healthy: Float,
)