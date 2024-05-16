package com.example.domain

import com.example.domain.model.NetworkResult
import com.example.domain.model.SearchImageData
import com.example.domain.model.SearchVClipData
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestSearchRepository: SearchRepository {
    override fun reqSearchImageData(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<NetworkResult<SearchImageData?>> = flow {
        NetworkResult.Success(
            SearchImageData(
                SearchImageData.Meta(
                    100,
                    5000,
                    true
                ),
                mutableListOf(
                    SearchImageData.DocumentsItem(
                        "collection",
                        "thumbnail_url",
                        "image_url",
                        500,
                        500,
                        "sitename",
                        "doc_url",
                        "datetime"
                    )
                )
            )
        )
    }

    override fun reqSearchVClipData(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<NetworkResult<SearchVClipData?>> = flow {
        NetworkResult.Success(
            SearchVClipData(
                SearchVClipData.Meta(
                    100,
                    5000,
                    false
                ),
                mutableListOf(
                    SearchVClipData.DocumentsItem(
                        "title",
                        500,
                        "thumbnail",
                        "url",
                        "datetime",
                        "author"
                    )
                )
            )
        )
    }
}