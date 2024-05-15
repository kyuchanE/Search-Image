package com.example.domain.usecase

import com.example.domain.model.CommonSearchResultData
import com.example.domain.model.NetworkResult
import com.example.domain.model.SearchImageData
import com.example.domain.model.SearchVClipData
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSearchItemsUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
) {
    operator fun invoke(
        query: String,
        page: Int,
        isImageResultPageEnd: Boolean = false,
        isVClipResultPageEnd: Boolean = false,
        sort: String = "recency",
        size: Int = 10,
    ): Flow<CommonSearchResultData> =
        if (!isImageResultPageEnd && !isVClipResultPageEnd) {
            searchRepository.reqSearchImageData(query, sort, page, size)
                .combineToVClipResult(
                    searchRepository.reqSearchVClipData(query, sort, page, size),
                    page
                )
        } else if(!isImageResultPageEnd) {
            searchRepository.reqSearchImageData(query, sort, page, size).convertResult(
                page
            )
        } else {
            searchRepository.reqSearchVClipData(query, sort, page, size).convertResult(
                page
            )
        }

}

@JvmName("searchImageToResultDataFlow")
private fun Flow<NetworkResult<SearchImageData?>>.convertResult(
    page: Int
): Flow<CommonSearchResultData> = flow {
    val dataList = mutableListOf<CommonSearchResultData.CommonSearchData>()
    collectLatest { imageResult ->
        if (imageResult.message == null) {
            imageResult.data?.let { searchImageData ->
                searchImageData.documents.forEach {
                    dataList.add(
                        CommonSearchResultData.CommonSearchData(
                            datetime = it.datetime,
                            title = it.display_sitename,
                            imgUrl = it.thumbnail_url,
                            url = it.doc_url,
                            category = it.collection,
                        )
                    )
                }
            }

            dataList.sortBy { it.datetime }

            CommonSearchResultData(
                data = dataList,
                errorCode = null,
                errorMsg = null,
                isImageResultPageEnd = imageResult.data?.meta?.is_end ?: false,
                page = page,
            )
        } else {
            CommonSearchResultData(
                data = null,
                errorCode = imageResult.code,
                errorMsg = imageResult.message,
                page = page,
            )
        }
    }
}

@JvmName("searchVClipToResultDataFlow")
private fun Flow<NetworkResult<SearchVClipData?>>.convertResult(
    page: Int,
): Flow<CommonSearchResultData> = flow {
    val dataList = mutableListOf<CommonSearchResultData.CommonSearchData>()
    collectLatest { vclipResult ->
        if (vclipResult.message == null) {
            vclipResult.data?.let { searchVClipData ->
                searchVClipData.documents.forEach {
                    dataList.add(
                        CommonSearchResultData.CommonSearchData(
                           datetime = it.datetime,
                           title = it.title,
                           imgUrl = it.thumbnail,
                           url = it.url,
                           category = null,
                        )
                    )
                }
            }

            dataList.sortBy { it.datetime }

            CommonSearchResultData(
                data = dataList,
                errorCode = null,
                errorMsg = null,
                isVClipResultPageEnd = vclipResult.data?.meta?.is_end ?: false,
                page = page,
            )
        } else {
            CommonSearchResultData(
                data = null,
                errorCode = vclipResult.code,
                errorMsg = vclipResult.message,
                page = page,
            )
        }
    }
}

private fun Flow<NetworkResult<SearchImageData?>>.combineToVClipResult(
    vClipFlow: Flow<NetworkResult<SearchVClipData?>>,
    page: Int,
): Flow<CommonSearchResultData> =
    combine(vClipFlow) { imageResult, vclipResult ->
        if (imageResult.message == null && vclipResult.message == null) {
            val dataList = mutableListOf<CommonSearchResultData.CommonSearchData>()
            imageResult.data?.let { searchImageData ->
                searchImageData.documents.forEach {
                    dataList.add(
                        CommonSearchResultData.CommonSearchData(
                            datetime = it.datetime,
                            title = it.display_sitename,
                            imgUrl = it.thumbnail_url,
                            url = it.doc_url,
                            category = it.collection,
                        )
                    )
                }
            }
            vclipResult.data?.let { searchVClipData ->
                searchVClipData.documents.forEach {
                    dataList.add(
                        CommonSearchResultData.CommonSearchData(
                            datetime = it.datetime,
                            title = it.title,
                            imgUrl = it.thumbnail,
                            url = it.url,
                            category = null,
                        )
                    )
                }
            }

            dataList.sortBy { it.datetime }

            CommonSearchResultData(
                data = dataList,
                errorCode = null,
                errorMsg = null,
                isImageResultPageEnd = imageResult.data?.meta?.is_end ?: false,
                isVClipResultPageEnd = vclipResult.data?.meta?.is_end ?: false,
                page = page,
            )
        } else {
            if (imageResult.message != null) {
                CommonSearchResultData(
                    data = null,
                    errorCode = imageResult.code,
                    errorMsg = imageResult.message,
                    page = page,
                )
            } else {
                CommonSearchResultData(
                    data = null,
                    errorCode = vclipResult.code,
                    errorMsg = vclipResult.message,
                    page = page,
                )
            }
        }
    }
