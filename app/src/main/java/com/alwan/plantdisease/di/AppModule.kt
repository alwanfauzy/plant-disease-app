package com.alwan.plantdisease.di

import com.alwan.plantdisease.core.domain.usecase.FlaskInteractor
import com.alwan.plantdisease.core.domain.usecase.FlaskUseCase
import com.alwan.plantdisease.core.domain.usecase.PreferencesInteractor
import com.alwan.plantdisease.core.domain.usecase.PreferencesUseCase
import com.alwan.plantdisease.core.domain.usecase.WeatherInteractor
import com.alwan.plantdisease.core.domain.usecase.WeatherUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    @ViewModelScoped
    abstract fun bindWeatherUseCase(weathterInteractor: WeatherInteractor): WeatherUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindFlaskUseCase(flaskInteractor: FlaskInteractor): FlaskUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindPreferencesUseCase(preferencesInteractor: PreferencesInteractor): PreferencesUseCase
}