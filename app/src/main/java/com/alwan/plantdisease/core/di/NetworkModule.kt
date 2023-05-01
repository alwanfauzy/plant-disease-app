package com.alwan.plantdisease.core.di

import com.alwan.plantdisease.BuildConfig
import com.alwan.plantdisease.core.data.remote.network.ApiService
import com.alwan.plantdisease.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

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
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()
}