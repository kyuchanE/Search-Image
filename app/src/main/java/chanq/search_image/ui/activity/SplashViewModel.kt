package chanq.search_image.ui.activity

import androidx.lifecycle.viewModelScope
import com.example.domain.model.CommonSearchResultData
import com.example.domain.model.SearchImageData
import com.example.domain.model.SearchVClipData
import com.example.domain.usecase.GetSearchImageUseCase
import com.example.domain.usecase.GetSearchVClipUseCase
import com.example.ui.common.BaseViewModel
import com.example.ui.utils.L
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getSearchImageUseCase: GetSearchImageUseCase,
    private val getSearchVClipUseCase: GetSearchVClipUseCase,
): BaseViewModel(){

//    fun fetchTest(
//        query: String,
//        sort: String,
//        page: Int,
//        size: Int,
//    ) {
//        viewModelScope.launch {
//            val deferredSearchImage: Deferred<CommonSearchResultData> = async {
//                var searchImageResult = CommonSearchResultData()
//                getSearchImageUseCase.invoke(
//                    query, sort, page, size
//                ).catch {
//                    searchImageResult = CommonSearchResultData(data = null, errorMsg = it.message)
//                }.collectLatest { searchImageData ->
//                    val data = mutableListOf<CommonSearchResultData.CommonSearchData>()
//                    searchImageData?.documents?.forEach {
//                        data.add(
//                            CommonSearchResultData.CommonSearchData(
//                                datetime = it.datetime,
//                                title = it.display_sitename,
//                                imgUrl = it.thumbnail_url,
//                                url = it.doc_url,
//                                category = it.collection,
//                            )
//                        )
//                    }
//
//                    searchImageResult = CommonSearchResultData(data = data, errorMsg = null)
//                }
//
//                return@async searchImageResult
//            }
//
//            val deferredSearchVClip: Deferred<CommonSearchResultData> = async {
//                var searchVClipResult = CommonSearchResultData()
//                getSearchVClipUseCase.invoke(
//                    query, sort, page, size
//                ).catch {
//                    searchVClipResult = CommonSearchResultData(data = null, errorMsg = it.message)
//                }.collectLatest { searchVClipData ->
//                    val data = mutableListOf<CommonSearchResultData.CommonSearchData>()
//                    searchVClipData?.documents?.forEach {
//                        data.add(
//                            CommonSearchResultData.CommonSearchData(
//                                datetime = it.datetime,
//                                title = it.title,
//                                imgUrl = it.thumbnail,
//                                url = it.url,
//                                category = null,
//                            )
//                        )
//                    }
//
//                    searchVClipResult = CommonSearchResultData(data = data, errorMsg = null)
//                }
//
//                return@async searchVClipResult
//            }
//
//            val deferredSearchImageResult = deferredSearchImage.await()
//            val deferredSearchVClipResult = deferredSearchVClip.await()
//
//            if (deferredSearchImageResult.errorMsg == null && deferredSearchVClipResult.errorMsg == null) {
//                val dataList = mutableListOf<CommonSearchResultData.CommonSearchData>()
//                deferredSearchImageResult.data?.let {
//                    dataList.addAll(it)
//                }
//                deferredSearchVClipResult.data?.let {
//                    dataList.addAll(it)
//                }
//
//                L.d("dataList : $dataList")
//                dataList.sortBy { it.datetime }
//                L.d("dataSortedList: $dataList")
//
//            } else {
//                // Error
//            }
//
//        }
//    }
}