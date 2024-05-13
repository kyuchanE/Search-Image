package com.example.data.model

import com.example.domain.model.SearchImageData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchImageResponse(
    val meta: Meta,
    val documents: MutableList<DocumentsItem>,
) {
    data class Meta(
        val total_count: Int,
        val pageable_count: Int,
        val is_end: Boolean,
    )

    data class DocumentsItem(
        val collection: String,
        val thumbnail_url: String,
        val image_url: String,
        val width: Int,
        val height: Int,
        val display_sitename: String,
        val doc_url: String,
        val datetime: String,
    )

    fun toDomain(): SearchImageData {
        val returnItemsList = mutableListOf<SearchImageData.DocumentsItem>()
        this.documents.forEach {
            returnItemsList.add(
                SearchImageData.DocumentsItem(
                    collection = it.collection,
                    thumbnail_url = it.thumbnail_url,
                    image_url = it.image_url,
                    width = it.width,
                    height = it.height,
                    display_sitename = it.display_sitename,
                    doc_url = it.doc_url,
                    datetime = it.datetime
                )
            )
        }

        return SearchImageData(
            meta = SearchImageData.Meta(
                this.meta.total_count,
                this.meta.pageable_count,
                this.meta.is_end
            ),
            documents = returnItemsList
        )
    }
}
