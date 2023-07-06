package com.alwan.plantdisease.core.domain.entity.flask

data class PotatoDisease(
    val diseasePredicted: String,
    val earlyBlight: Float,
    val healthy: Float,
    val lateBlight: Float,
)