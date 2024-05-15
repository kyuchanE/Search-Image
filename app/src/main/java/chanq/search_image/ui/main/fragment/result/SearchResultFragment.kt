package chanq.search_image.ui.main.fragment.result

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import chanq.search_image.R
import chanq.search_image.databinding.FragmentSearchResultBinding
import chanq.search_image.di.App
import com.example.domain.model.CommonSearchResultData
import com.example.domain.model.NetworkResult
import chanq.search_image.ui.main.MainActivity
import chanq.search_image.ui.main.MainViewModel
import com.example.ui.common.BaseFragment
import com.example.ui.utils.L
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchResultFragment: BaseFragment<FragmentSearchResultBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_search_result

    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var searchResultAdapter: SearchResultAdapter


    override fun initFragmentView() {
        binding.searchStr = baseActivity.getString(R.string.feature_main_search_hint)

        searchResultAdapter = SearchResultAdapter(
            searchResultModelList = mutableListOf(),
            searchResultClickEvent = { searchData ->
                L.d("SearchResultAdapter click : ${searchData.title}")
            },
            favoritesClickEvent = { searchData ->
                L.d("favoritesClickEvent click : ${searchData.title}")
                App.preferences.setFavoriteList(searchData)
            }
        )

        with(binding.rvSearchResult) {
            adapter = searchResultAdapter
            setOnScrollChangeListener { _, _, _, _, _ ->
                // RecyclerView 마지막 아이템 값이 화면에 보이기 시작하면 페이징 시작
                var findLastVisiblePosition = (this.layoutManager as LinearLayoutManager)
                    .findLastVisibleItemPosition()

                if (findLastVisiblePosition in searchResultAdapter.itemCount - 1 .. searchResultAdapter.itemCount) {
                    (baseActivity as MainActivity).reqFetchSearch("고양이")
                }
            }
        }

        binding.llSearch.setOnClickListener {
            (baseActivity as MainActivity).goSearchPage(binding.tvSearch.text.toString())
        }
    }

    override fun initObserve() {
        super.initObserve()

        lifecycleScope.launch {
            mainViewModel.searchNetworkResult.collect { result -> handleSearchResult(result)}
        }
    }

    private fun handleSearchResult(result: NetworkResult<CommonSearchResultData>) =
        when (result) {
            is NetworkResult.Loading -> {
                L.d("SearchResultFragment Loading : @@@@@@@ ")
            }
            is NetworkResult.Error -> {
                L.d("SearchResultFragment Error : ${result.errorcode} >> ${result.msg}")
            }
            is NetworkResult.Success -> {
                L.d("SearchResultFragment Success : $result ")
                val searchResultModelList = mutableListOf<SearchResultModel>()
                result.data?.let { searchResultData ->
                    searchResultData.data?.let {
                        searchResultModelList.add(      // 검색 결과 리스트
                            SearchResultModel.SearchResultList(itemList = it)
                        )
                        searchResultModelList.add(      // 하단 페이지 구분자 + loading icon
                            SearchResultModel.SearchResultDivider(cntPage = searchResultData.page.toString())
                        )
                    }
                    if (searchResultAdapter.itemCount > 0)  // 페이지 구분 하단 로딩 View 제거
                        searchResultAdapter.finishedLoading()
                    if (searchResultData.page == 1) {       // 새로운 검색 결과 (page == 1)
                        searchResultAdapter.addItemListAfterClear(searchResultModelList)
                    } else {                                // 기존 검색 결과의 다른 페이지
                        searchResultAdapter.addItemList(searchResultModelList)
                    }
                }
            }
        }

    fun changeSearchStr(str: String) {
        binding.searchStr = str
    }

    fun moveScrollTop() {
        binding.rvSearchResult.smoothScrollToPosition(0)
    }
}