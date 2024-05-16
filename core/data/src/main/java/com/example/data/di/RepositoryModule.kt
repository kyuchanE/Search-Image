package com.example.data.di

import com.example.data.repository.FavoritesRepositoryImpl
import com.example.data.repository.SearchRepositoryImpl
import com.example.domain.repository.FavoritesRepository
import com.example.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    fun bindFavoritesRepository(favoritesRepositoryImpl: FavoritesRepositoryImpl): FavoritesRepository
}