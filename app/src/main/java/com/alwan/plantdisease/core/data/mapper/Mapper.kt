package com.alwan.plantdisease.core.data.mapper

import com.alwan.plantdisease.core.data.remote.response.weather.Coord
import com.alwan.plantdisease.core.data.remote.response.weather.WeatherResponse
import com.alwan.plantdisease.core.domain.entity.weather.Coordinate
import com.alwan.plantdisease.core.domain.entity.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

// Weather Mapper

fun WeatherResponse.mapToDomain(): Flow<WeatherInfo> =
    flowOf(
        WeatherInfo(
            base = base,
            clouds = clouds.all,
            cod = cod,
            coord = mapCoordinate(coord),
            dt = dt,
            id = id,
            temperature = main.temp,
            humidity = main.humidity,
            name = name,
            timezone = timezone,
            visibility = visibility,
            windSpeed = wind.speed
        )
    )


private fun mapCoordinate(from: Coord) = Coordinate(from.lat, from.lon)
