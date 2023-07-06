package com.alwan.plantdisease.core.domain.usecase

import com.alwan.plantdisease.core.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PreferencesInteractor @Inject constructor(private val preferencesRepository: PreferencesRepository) :
    PreferencesUseCase {
    override suspend fun saveBaseUrl(baseUrl: String) = preferencesRepository.saveBaseUrl(baseUrl)

    override fun getBaseUrl(): Flow<String> = preferencesRepository.getBaseUrl()

    override suspend fun savePlantCategory(category: String) =
        preferencesRepository.savePlantCategory(category)

    override fun getPlantCategory(): Flow<String> = preferencesRepository.getPlantCategory()
}