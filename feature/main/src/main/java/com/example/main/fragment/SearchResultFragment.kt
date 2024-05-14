package com.example.main.fragment

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.main.R
import com.example.main.activity.MainViewModel
import com.example.main.databinding.FragmentSearchResultBinding
import com.example.ui.common.BaseFragment
import com.example.ui.utils.L
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchResultFragment: BaseFragment<FragmentSearchResultBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_search_result

    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var searchResultAdapter: SearchResultAdapter

    override fun initView() {
        L.d("initView")

        searchResultAdapter = SearchResultAdapter(
            mutableListOf()
        ) { searchData ->
            L.d("SearchResultAdapter click : ${searchData.title}")
        }

        with(binding.rvSearchResult) {
            adapter = searchResultAdapter
        }
    }

    override fun initObserve() {
        super.initObserve()

        lifecycleScope.launch {
            mainViewModel.searchResult.collect { searchData ->
                searchData.data?.let {
                    searchResultAdapter.addItemList(it)
                }
            }
        }

    }
}