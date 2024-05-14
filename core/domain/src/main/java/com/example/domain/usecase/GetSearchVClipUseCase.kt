package com.example.domain.usecase

import com.example.domain.model.NetworkResult
import com.example.domain.model.SearchVClipData
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchVClipUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
) {
    operator fun invoke(
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ): Flow<NetworkResult<SearchVClipData?>> = searchRepository.reqSearchVClipData(
        query,
        sort,
        page,
        size,
    )
}