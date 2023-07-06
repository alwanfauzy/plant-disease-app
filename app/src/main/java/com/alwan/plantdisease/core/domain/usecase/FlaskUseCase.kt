package com.alwan.plantdisease.core.domain.usecase

import com.alwan.plantdisease.core.data.util.Resource
import com.alwan.plantdisease.core.domain.entity.flask.CornDisease
import com.alwan.plantdisease.core.domain.entity.flask.PotatoDisease
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface FlaskUseCase {
    fun classifyPotato(image: MultipartBody.Part): Flow<Resource<PotatoDisease>>

    fun classifyCorn(image: MultipartBody.Part): Flow<Resource<CornDisease>>
}