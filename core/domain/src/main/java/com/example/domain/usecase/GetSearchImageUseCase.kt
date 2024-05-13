package com.example.domain.usecase

import com.example.domain.model.SearchImageData
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchImageUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
) {
    operator fun invoke(
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ): Flow<SearchImageData?> = searchRepository.reqSearchImageData(
        query,
        sort,
        page,
        size,
    )
}