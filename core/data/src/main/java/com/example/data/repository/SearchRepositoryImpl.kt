package com.example.data.repository

import com.example.data.api.SearchApi
import com.example.domain.model.NetworkResult
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
    ): Flow<NetworkResult<SearchImageData?>> = flow {
        val response = searchApi.reqSearchImage(
            query,
            sort,
            page,
            size,
        )

        when(response.code()) {
            in 200..299 -> emit(NetworkResult.Success(response.body()?.toDomain()))
            else -> {
                emit(NetworkResult.Error(response.code().toString(), response.errorBody().toString()))
            }
        }
    }

    override fun reqSearchVClipData(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<NetworkResult<SearchVClipData?>> = flow {
        val response = searchApi.reqSearchVClip(
            query,
            sort,
            page,
            size,
        )

        when(response.code()) {
            in 200..299 -> emit(NetworkResult.Success(response.body()?.toDomain()))
            else -> {
                emit(NetworkResult.Error(response.code().toString(), response.errorBody().toString()))
            }
        }
    }
}