package com.example.domain.model

data class CommonSearchResultData (
    val data: MutableList<CommonSearchData>? = null,
    val errorMsg: String? = null,
) {

    data class CommonSearchData (
        val datetime: String,
        val title: String,
        val imgUrl: String,
        val url: String,
        val category: String? =  null,
    )
}