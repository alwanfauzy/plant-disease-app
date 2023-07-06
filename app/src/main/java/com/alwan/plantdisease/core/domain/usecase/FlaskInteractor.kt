package com.alwan.plantdisease.core.domain.usecase

import com.alwan.plantdisease.core.data.util.Resource
import com.alwan.plantdisease.core.domain.entity.flask.CornDisease
import com.alwan.plantdisease.core.domain.entity.flask.PotatoDisease
import com.alwan.plantdisease.core.domain.repository.FlaskRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class FlaskInteractor @Inject constructor(private val flaskRepository: FlaskRepository) :
    FlaskUseCase {
    override fun classifyPotato(image: MultipartBody.Part): Flow<Resource<PotatoDisease>> =
        flaskRepository.classifyPotato(image)

    override fun classifyCorn(image: MultipartBody.Part): Flow<Resource<CornDisease>> =
        flaskRepository.classifyCorn(image)
}