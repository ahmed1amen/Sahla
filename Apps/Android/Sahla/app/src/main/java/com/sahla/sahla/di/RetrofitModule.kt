package com.sahla.sahla.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sahla.sahla.data.remote.api.ApiService
import com.sahla.sahla.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RetrofitModule {
    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .client(httpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(Gson()))

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit.Builder): ApiService =
        retrofit.build().create(ApiService::class.java)
}