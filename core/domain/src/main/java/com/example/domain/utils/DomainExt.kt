package com.example.domain.utils

import com.example.domain.model.CommonSearchResultData

fun CommonSearchResultData.CommonSearchData.compareFavorite(data: CommonSearchResultData.CommonSearchData): Boolean =
    this.datetime == data.datetime && this.title == data.title && this.url == data.url && this.category == data.category && this.imgUrl == data.imgUrl
