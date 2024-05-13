package com.example.data.model

import com.example.domain.model.SearchVClipData
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchVClipResponse(
    val meta: Meta,
    val documents: MutableList<DocumentsItem>,
) {
    data class Meta(
        val total_count: Int,
        val pageable_count: Int,
        val is_end: Boolean,
    )

    data class DocumentsItem(
        val title: String,
        val play_time: Int,
        val thumbnail: String,
        val url: String,
        val datetime: String,
        val author: String,
    )

    fun toDomain(): SearchVClipData {
        val returnItemsList = mutableListOf<SearchVClipData.DocumentsItem>()
        this.documents.forEach {
            returnItemsList.add(
                SearchVClipData.DocumentsItem(
                    title = it.title,
                    play_time = it.play_time,
                    thumbnail = it.thumbnail,
                    url = it.url,
                    datetime = it.datetime,
                    author = it.author
                )
            )
        }

        return SearchVClipData(
            meta = SearchVClipData.Meta(
                total_count = this.meta.total_count,
                pageable_count = this.meta.pageable_count,
                is_end = this.meta.is_end,
            ),
            documents = returnItemsList
        )
    }
}
