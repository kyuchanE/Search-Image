package com.example.main.activity

import androidx.lifecycle.viewModelScope
import com.example.domain.model.CommonSearchResultData
import com.example.domain.usecase.GetSearchImageUseCase
import com.example.domain.usecase.GetSearchVClipUseCase
import com.example.ui.common.BaseViewModel
import com.example.ui.utils.L
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSearchImageUseCase: GetSearchImageUseCase,
    private val getSearchVClipUseCase: GetSearchVClipUseCase,
): BaseViewModel() {

    private val _loadingController = MutableSharedFlow<Boolean>()
    var loadingController = _loadingController.asSharedFlow()

    private val _searchResult = MutableSharedFlow<CommonSearchResultData>()
    var searchResult = _searchResult.asSharedFlow()

    fun fetchSearchImage(
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ) {
        viewModelScope.launch {

            val deferredSearchImage: Deferred<CommonSearchResultData> = async {
                var searchImageResult = CommonSearchResultData()
                getSearchImageUseCase.invoke(
                    query, sort, page, size
                ).catch {
                    searchImageResult = CommonSearchResultData(data = null, errorMsg = it.message)
                }.collectLatest { searchImageNetworkResult ->
                    if (searchImageNetworkResult.message != null) {
                        searchImageResult = CommonSearchResultData(
                            data = null,
                            errorCode = searchImageNetworkResult.code,
                            errorMsg = searchImageNetworkResult.message
                        )
                    } else {
                        searchImageNetworkResult.data?.let { searchImageData ->
                            val data = mutableListOf<CommonSearchResultData.CommonSearchData>()
                            searchImageData.documents.forEach {
                                data.add(
                                    CommonSearchResultData.CommonSearchData(
                                        datetime = it.datetime,
                                        title = it.display_sitename,
                                        imgUrl = it.thumbnail_url,
                                        url = it.doc_url,
                                        category = it.collection,
                                    )
                                )
                            }
                            searchImageResult = CommonSearchResultData(
                                data = data,
                                errorCode = null,
                                errorMsg = null,
                                isImageResultPageEnd = searchImageData.meta.is_end,
                            )
                        }
                    }

                }

                return@async searchImageResult
            }

            val deferredSearchVClip: Deferred<CommonSearchResultData> = async {
                var searchVClipResult = CommonSearchResultData()
                getSearchVClipUseCase.invoke(
                    query, sort, page, size
                ).catch {
                    searchVClipResult = CommonSearchResultData(data = null, errorMsg = it.message)
                }.collectLatest { searchVClipNetworkResult ->
                    if (searchVClipNetworkResult.message != null) {
                        searchVClipResult = CommonSearchResultData(
                            data = null,
                            errorCode = searchVClipNetworkResult.code,
                            errorMsg = searchVClipNetworkResult.message
                        )
                    } else {
                        searchVClipNetworkResult.data?.let { searchVClipData ->
                            val data = mutableListOf<CommonSearchResultData.CommonSearchData>()
                            searchVClipData.documents.forEach {
                                data.add(
                                    CommonSearchResultData.CommonSearchData(
                                        datetime = it.datetime,
                                        title = it.title,
                                        imgUrl = it.thumbnail,
                                        url = it.url,
                                        category = null,
                                    )
                                )
                            }

                            searchVClipResult = CommonSearchResultData(
                                data = data,
                                errorCode = null,
                                errorMsg = null,
                                isVClipResultPageEnd = searchVClipData.meta.is_end
                            )
                        }
                    }
                }

                return@async searchVClipResult
            }

            val deferredSearchImageResult = deferredSearchImage.await()
            val deferredSearchVClipResult = deferredSearchVClip.await()

            if (deferredSearchImageResult.errorMsg == null && deferredSearchVClipResult.errorMsg == null) {
                val dataList = mutableListOf<CommonSearchResultData.CommonSearchData>()
                deferredSearchImageResult.data?.let {
                    dataList.addAll(it)
                }
                deferredSearchVClipResult.data?.let {
                    dataList.addAll(it)
                }

                dataList.sortBy { it.datetime }

                _searchResult.emit(
                    CommonSearchResultData(
                        data = dataList,
                        errorCode = null,
                        errorMsg = null,
                        isImageResultPageEnd = deferredSearchImageResult.isImageResultPageEnd,
                        isVClipResultPageEnd = deferredSearchVClipResult.isVClipResultPageEnd,
                    )
                )

            } else {
                // Error
            }

        }
    }
}