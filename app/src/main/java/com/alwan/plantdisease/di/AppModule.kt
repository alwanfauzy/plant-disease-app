package com.alwan.plantdisease.di

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
}