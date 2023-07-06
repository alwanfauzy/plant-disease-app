package com.alwan.plantdisease.core.domain.usecase

import kotlinx.coroutines.flow.Flow

interface PreferencesUseCase {
    suspend fun saveBaseUrl(baseUrl: String)

    fun getBaseUrl(): Flow<String>

    suspend fun savePlantCategory(category: String)

    fun getPlantCategory(): Flow<String>
}