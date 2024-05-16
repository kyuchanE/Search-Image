package chanq.search_image.ui.main

import androidx.lifecycle.viewModelScope
import com.example.domain.model.CommonSearchResultData
import com.example.domain.model.NetworkResult
import com.example.domain.repository.FavoritesRepository
import com.example.domain.usecase.GetSearchItemsUseCase
import com.example.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val favoritesRepository: FavoritesRepository,
): BaseViewModel() {

    private val _searchNetworkResult = MutableSharedFlow<NetworkResult<CommonSearchResultData>>()
    var searchNetworkResult = _searchNetworkResult.asSharedFlow()

    private val _favoritesList = MutableSharedFlow<MutableList<CommonSearchResultData.CommonSearchData>>()
    var favoritesList = _favoritesList.asSharedFlow()

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

    fun clickFavorites(item: CommonSearchResultData.CommonSearchData) {
        viewModelScope.launch {
            favoritesRepository.clickFavorite(item)
        }
    }

    fun getFavoritesList() {
        viewModelScope.launch {
            _favoritesList.emit(favoritesRepository.loadData())
        }
    }

}