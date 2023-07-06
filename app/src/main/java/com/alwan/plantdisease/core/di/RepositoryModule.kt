package com.alwan.plantdisease.core.di

import com.alwan.plantdisease.core.data.repository.WeatherRepositoryImpl
import com.alwan.plantdisease.core.data.repository.FlaskRepositoryImpl
import com.alwan.plantdisease.core.data.repository.PreferencesRepositoryImpl
import com.alwan.plantdisease.core.domain.repository.FlaskRepository
import com.alwan.plantdisease.core.domain.repository.WeatherRepository
import com.alwan.plantdisease.core.domain.repository.PreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, PreferencesModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(mainRepository: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindFlaskRepository(flaskRepository: FlaskRepositoryImpl): FlaskRepository

    @Binds
    @Singleton
    abstract fun bindPreferencesRepository(preferencesRepository: PreferencesRepositoryImpl): PreferencesRepository
}