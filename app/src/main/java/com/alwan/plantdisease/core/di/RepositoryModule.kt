package com.alwan.plantdisease.core.di

import com.alwan.plantdisease.core.data.MainRepositoryImpl
import com.alwan.plantdisease.core.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMainRepository(mainRepository: MainRepositoryImpl): MainRepository
}