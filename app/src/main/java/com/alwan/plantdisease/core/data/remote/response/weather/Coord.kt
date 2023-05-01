package com.alwan.plantdisease.core.data.remote.response.weather


import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double
)