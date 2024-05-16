package com.example.data.repository

import com.example.data.utils.PreferenceUtil
import com.example.domain.model.CommonSearchResultData
import com.example.domain.repository.FavoritesRepository
import com.example.domain.utils.compareFavorite
import com.google.gson.JsonObject
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val preferenceUtil: PreferenceUtil,
): FavoritesRepository {

    override fun saveData(
        item: CommonSearchResultData.CommonSearchData
    ) {
        preferenceUtil.setFavoriteList(item)
    }

    override fun clickFavorite(item: CommonSearchResultData.CommonSearchData) {
        var isSave = true
        var removeIndex: Int? = null

        val prevlist = loadData()

        prevlist.forEachIndexed { index, commonSearchData ->
            if (commonSearchData.compareFavorite(item)) {
                isSave = false
                removeIndex = index
                return@forEachIndexed
            }
        }

        if (isSave) {
            preferenceUtil.setFavoriteList(item)
        } else {
            preferenceUtil.removeFavoriteList()
            prevlist.forEachIndexed { index, commonSearchData ->
                if (removeIndex != index) {
                    preferenceUtil.setFavoriteList(commonSearchData)
                }
            }
        }
    }

    override fun loadData(): MutableList<CommonSearchResultData.CommonSearchData> {
        val returnList = mutableListOf<CommonSearchResultData.CommonSearchData>()

        preferenceUtil.getFavoriteList().forEach {
            returnList.add(
                CommonSearchResultData.CommonSearchData(
                    datetime = it.getConvertString("datetime"),
                    title = it.getConvertString("title"),
                    imgUrl = it.getConvertString("imgUrl"),
                    url = it.getConvertString("url"),
                    category = it.getConvertString("category").ifEmpty { null },
                    isFavorite = true
                )
            )
        }

        return returnList
    }

    private fun JsonObject.getConvertString(key: String): String {
        var returnStr = ""
        try {
            returnStr = get(key).asString
        } catch (e: Exception) {

        }

        return returnStr
    }

}