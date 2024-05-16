package com.example.domain.repository

import com.example.domain.model.CommonSearchResultData
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    fun saveData(
        item: CommonSearchResultData.CommonSearchData
    )

    fun clickFavorite(
        item: CommonSearchResultData.CommonSearchData
    )

    fun loadData(): MutableList<CommonSearchResultData.CommonSearchData>

}