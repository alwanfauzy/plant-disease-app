package com.alwan.plantdisease.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alwan.plantdisease.core.data.util.Resource
import com.alwan.plantdisease.core.domain.entity.flask.CornDisease
import com.alwan.plantdisease.core.domain.entity.flask.PotatoDisease
import com.alwan.plantdisease.core.domain.usecase.FlaskUseCase
import com.alwan.plantdisease.core.domain.usecase.PreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val flaskUseCase: FlaskUseCase,
    private val preferencesUseCase: PreferencesUseCase
) : ViewModel() {
    fun classifyPotato(image: MultipartBody.Part): LiveData<Resource<PotatoDisease>> =
        flaskUseCase.classifyPotato(image).asLiveData()

    fun classifyCorn(image: MultipartBody.Part): LiveData<Resource<CornDisease>> =
        flaskUseCase.classifyCorn(image).asLiveData()

    suspend fun saveBaseUrl(baseUrl: String) = preferencesUseCase.saveBaseUrl(baseUrl)

    fun getBaseUrl(): LiveData<String> = preferencesUseCase.getBaseUrl().asLiveData()

    suspend fun savePlantCategory(category: String) = preferencesUseCase.savePlantCategory(category)

    fun getPlantCategory(): LiveData<String> = preferencesUseCase.getPlantCategory().asLiveData()
}