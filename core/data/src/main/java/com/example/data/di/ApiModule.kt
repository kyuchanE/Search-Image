package com.example.data.di

import com.example.data.api.SearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideSearchApiService(provideSearchRetrofit: Retrofit): SearchApi =
        provideSearchRetrofit.create(SearchApi::class.java)
}