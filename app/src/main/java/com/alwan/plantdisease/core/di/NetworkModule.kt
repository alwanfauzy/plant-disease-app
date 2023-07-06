package com.alwan.plantdisease.core.di

import com.alwan.plantdisease.BuildConfig
import com.alwan.plantdisease.core.data.local.preferences.PreferencesDataStore
import com.alwan.plantdisease.core.data.remote.apiservice.FlaskApiService
import com.alwan.plantdisease.core.data.remote.apiservice.ApiService
import com.alwan.plantdisease.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(loggingInterceptor))
            .connectTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideFlaskApiService(@Named(Constant.FLASK) retrofit: Retrofit): FlaskApiService =
        retrofit.create(FlaskApiService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(Constant.WEATHER_BASE_URL)
        .addConverterFactory(gsonConverterFactory)
        .client(client)
        .build()

    @Provides
    @Singleton
    @Named(Constant.FLASK)
    fun provideFlaskRetrofit(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        preferencesDataStore: PreferencesDataStore,
    ): Retrofit {
        val baseUrl = runBlocking { preferencesDataStore.baseUrlFlow.first() }

        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()
}