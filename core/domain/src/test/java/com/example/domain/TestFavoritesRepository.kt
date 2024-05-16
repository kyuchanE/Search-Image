package com.example.domain

import android.util.Log
import com.example.domain.model.CommonSearchResultData
import com.example.domain.repository.FavoritesRepository

class TestFavoritesRepository: FavoritesRepository {
    override fun saveData(item: CommonSearchResultData.CommonSearchData) {
        Log.d("TestFavoritesRepository", "TEST SaveData")
    }

    override fun clickFavorite(item: CommonSearchResultData.CommonSearchData) {
        Log.d("TestFavoritesRepository", "TEST clickFavorite")
    }

    override fun loadData(): MutableList<CommonSearchResultData.CommonSearchData> =
        mutableListOf()
}