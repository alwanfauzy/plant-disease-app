package com.alwan.plantdisease.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    suspend fun saveBaseUrl(baseUrl: String)

    fun getBaseUrl(): Flow<String>

    suspend fun savePlantCategory(category: String)

    fun getPlantCategory(): Flow<String>
}