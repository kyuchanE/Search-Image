package com.example.domain.model

data class SearchImageData(
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
}
