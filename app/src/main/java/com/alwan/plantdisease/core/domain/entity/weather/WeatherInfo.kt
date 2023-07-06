package com.alwan.plantdisease.core.domain.entity.weather

data class WeatherInfo (
    val base: String,
    val clouds: Int,
    val cod: Int,
    val coord: Coordinate,
    val dt: Int,
    val id: Int,
    val temperature: Double,
    val humidity: Int,
    val name: String,
    val timezone: Int,
    val visibility: Int,
    val windSpeed: Double
)