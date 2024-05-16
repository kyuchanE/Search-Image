package com.example.detail

import com.example.domain.model.CommonSearchResultData

object DetailObject {
    private var detailItemData: CommonSearchResultData.CommonSearchData? = null

    fun setDetailData(item: CommonSearchResultData.CommonSearchData) {
        detailItemData = item
    }

    fun getDetailData(): CommonSearchResultData.CommonSearchData? = detailItemData

    fun changeFavorite(isFavorite: Boolean) {
        detailItemData?.let {
            it.isFavorite = isFavorite
        }
    }

    fun clear() {
        detailItemData = null
    }
}