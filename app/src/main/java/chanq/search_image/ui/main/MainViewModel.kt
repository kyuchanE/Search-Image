package chanq.search_image.ui.main

import androidx.lifecycle.viewModelScope
import com.example.domain.model.CommonSearchResultData
import com.example.domain.model.NetworkResult
import com.example.domain.usecase.GetSearchImageUseCase
import com.example.domain.usecase.GetSearchItemsUseCase
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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSearchItemsUseCase: GetSearchItemsUseCase,
): BaseViewModel() {

    private val _searchNetworkResult = MutableSharedFlow<NetworkResult<CommonSearchResultData>>()
    var searchNetworkResult = _searchNetworkResult.asSharedFlow()

    private var cntPage = 1
    private var isImageResultPageEnd: Boolean = false
    private var isVClipResultPageEnd: Boolean = false

    fun fetchSearchImage(
        query: String,
        isPageClear: Boolean = false,
    ) {
        if (isPageClear) {
            isImageResultPageEnd = false
            isVClipResultPageEnd = false
            cntPage = 1
        }

        viewModelScope.launch {
            if (!isImageResultPageEnd || !isVClipResultPageEnd) {
                getSearchItemsUseCase(
                    query = query,
                    page = cntPage,
                    isImageResultPageEnd = isImageResultPageEnd,
                    isVClipResultPageEnd = isVClipResultPageEnd,
                ).onStart {
                    _searchNetworkResult.emit(
                        NetworkResult.Loading()
                    )
                }.catch {
                    _searchNetworkResult.emit(
                        NetworkResult.Error(
                            errorcode = "",
                            msg = it.message,
                        )
                    )
                }.collectLatest { searchResultData ->
                    if (searchResultData.errorMsg == null) {
                        cntPage++
                        isImageResultPageEnd = searchResultData.isImageResultPageEnd ?: false
                        isVClipResultPageEnd = searchResultData.isVClipResultPageEnd ?: false
                        _searchNetworkResult.emit(
                            NetworkResult.Success(searchResultData)
                        )
                    } else {
                        _searchNetworkResult.emit(
                            NetworkResult.Error(
                                errorcode = searchResultData.errorCode,
                                msg = searchResultData.errorMsg,
                            )
                        )
                    }
                }
            }
        }
    }
}