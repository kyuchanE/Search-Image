package com.example.domain.model

data class CommonSearchResultData (
    val data: MutableList<CommonSearchData>? = null,
    val errorCode: String? = null,
    val errorMsg: String? = null,
    val isImageResultPageEnd: Boolean? = null,
    val isVClipResultPageEnd: Boolean? = null,
    val page: Int = 0,
) {

    data class CommonSearchData (
        val datetime: String,
        val title: String,
        val imgUrl: String,
        val url: String,
        val category: String? =  null,
        var isFavorite: Boolean = false,
        var isShowPage: Boolean = false,
        var page: Int? = null,
    )
}