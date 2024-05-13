package com.example.domain.model

data class SearchVClipData(
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
}
