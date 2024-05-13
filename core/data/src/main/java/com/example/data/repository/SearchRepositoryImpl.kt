package com.example.data.repository

import com.example.data.api.SearchApi
import com.example.domain.model.SearchImageData
import com.example.domain.model.SearchVClipData
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi,
): SearchRepository {

    override fun reqSearchImageData(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<SearchImageData?> = flow {
        val response = searchApi.reqSearchImage(
            query,
            sort,
            page,
            size,
        )

        when(response.code()) {
            in 200..299 -> emit(response.body()?.toDomain())
            else -> {

            }
        }
    }

    override fun reqSearchVClipData(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<SearchVClipData?> = flow {
        val response = searchApi.reqSearchVClip(
            query,
            sort,
            page,
            size,
        )

        when(response.code()) {
            in 200..299 -> emit(response.body()?.toDomain())
            else -> {

            }
        }
    }
}