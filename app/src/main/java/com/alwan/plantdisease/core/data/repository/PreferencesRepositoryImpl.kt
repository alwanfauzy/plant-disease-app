package com.alwan.plantdisease.core.data.repository

import com.alwan.plantdisease.core.data.local.preferences.PreferencesDataStore
import com.alwan.plantdisease.core.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(private val preferencesDataStore: PreferencesDataStore) :
    PreferencesRepository {
    override suspend fun saveBaseUrl(baseUrl: String) {
        preferencesDataStore.saveBaseUrl(baseUrl)
    }

    override fun getBaseUrl(): Flow<String> = preferencesDataStore.baseUrlFlow

    override suspend fun savePlantCategory(category: String) {
        preferencesDataStore.savePlantCategory(category)
    }

    override fun getPlantCategory(): Flow<String> = preferencesDataStore.plantCategoryFlow
}