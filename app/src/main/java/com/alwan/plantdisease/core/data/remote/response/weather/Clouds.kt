package com.alwan.plantdisease.core.data.remote.response.weather


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)