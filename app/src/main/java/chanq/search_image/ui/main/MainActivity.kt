package chanq.search_image.ui.main

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import chanq.search_image.R
import chanq.search_image.databinding.ActivityMainBinding
import chanq.search_image.di.App
import chanq.search_image.ui.main.fragment.favorites.FavoritesFragment
import chanq.search_image.ui.main.fragment.result.SearchResultFragment
import com.example.data.utils.PreferenceUtil.Companion.KEY_FAVORITE
import com.example.detail.DetailActivity
import com.example.detail.DetailActivity.Companion.KEY_IMG_URL
import com.example.detail.DetailActivity.Companion.KEY_IS_FAVORITE
import com.example.detail.DetailActivity.Companion.KEY_TITLE
import com.example.detail.DetailActivity.Companion.RESULT_CODE_DETAIL
import com.example.domain.model.CommonSearchResultData
import com.example.domain.model.NetworkResult
import com.example.search.SearchActivity
import com.example.search.SearchActivity.Companion.KEY_SEARCH_STR
import com.example.search.SearchActivity.Companion.RESULT_CODE_SEARCH
import com.example.ui.common.BaseActivity
import com.example.ui.utils.L
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch

@AndroidEntryPoint
@WithFragmentBindings
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_main

    private lateinit var pageAdapter: MainPageAdapter

    private var isLoading: Boolean = false

    private var searchStr = ""

    private val viewModel by viewModels<MainViewModel>()

    /** init Fragment **/
    private val fragmentList = mutableListOf(
        SearchResultFragment() as Fragment,
        FavoritesFragment() as Fragment,
    )

    private val activityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        when(it.resultCode) {
            RESULT_CODE_SEARCH -> {     // 검색 페이지의 결과 값 전달
                it.data?.let {  intentData ->
                    val searchStr: String = intentData.getStringExtra(KEY_SEARCH_STR)?.trim() ?: ""
                    if (searchStr.isNotEmpty()) {
                        (fragmentList[0] as SearchResultFragment).changeSearchStr(searchStr)
                        showLoading()
                        reqFetchSearch(query = searchStr, isPageClear = true)
                    }
                }
            }
            RESULT_CODE_DETAIL -> {         // 상세 페이지

            }
        }
    }

    override fun initView() {
        binding.isSelectedSearch = true

        initPageAdapter()
        initButtonClickListener()

        showLoading()
        searchStr = "고양이"
        viewModel.fetchSearchImage( query = searchStr, isPageClear = true )

        L.d("@@@@@@@ MY_FAVORITE : ${App.preferences.getFavoriteList()}")
    }

    override fun initObserve() {
        super.initObserve()

        lifecycleScope.launch {
            viewModel.searchNetworkResult.collect { result -> handleSearchResult(result)}
        }
    }

    private fun handleSearchResult(result: NetworkResult<CommonSearchResultData>) =
        when (result) {
            is NetworkResult.Loading -> {
                L.d("MainActivity Loading : @@@@@@@ ")
                isLoading = true
            }
            is NetworkResult.Error -> {
                L.d("MainActivity Error : ${result.errorcode} >> ${result.msg}")
                isLoading = false
                hideLoading()
            }
            is NetworkResult.Success -> {
                L.d("MainActivity Success : $result ")
                isLoading = false
                hideLoading()
            }
        }

    private fun initPageAdapter() {
        pageAdapter = MainPageAdapter(this, fragmentList)
        with(binding.vpMain) {
            adapter = pageAdapter
            isUserInputEnabled = false      // 스와이프 막기
            offscreenPageLimit = 2
        }
    }

    private fun initButtonClickListener() {

        binding.btnTabSearch.setOnClickListener {
            switchTab(true)
        }
        binding.btnTabFavorites.setOnClickListener {
            switchTab(false)
        }

    }

    /**
     * 상단 탭 클릭 액션
     */
    private fun switchTab(isSelectedSearch: Boolean) {
        binding.isSelectedSearch = isSelectedSearch

        if (!binding.btnTabSearch.isSelected && isSelectedSearch) { // 검색 탭
            showLoading()
            animTabBar(true)
            binding.vpMain.setCurrentItem(0, false)
            viewModel.fetchSearchImage( query = searchStr, isPageClear = true )
        } else if (binding.vpMain.currentItem == 0 && isSelectedSearch) {   // 검색 탭 중복
            (fragmentList[0] as SearchResultFragment).moveScrollTop()
        } else if (!binding.btnTabFavorites.isSelected && !isSelectedSearch) {  // 즐겨찾기 탭
            animTabBar(false)
            binding.vpMain.setCurrentItem(1, false)
            viewModel.getFavoritesList()
        }
    }

    /**
     * 상단 탭 클릭 애니메이션
     */
    private fun animTabBar(moveRight: Boolean) {
        val value = if (moveRight) 0.0f else binding.viewV.x - (binding.viewAnimBar.x)
        ObjectAnimator.ofFloat(binding.viewAnimBar, "translationX", value).apply {
            duration = 200
        }.start()
    }

    /**
     * 검색 결과 호출
     */
    fun reqFetchSearch(
        query: String = "",
        isPageClear: Boolean = false,
    ) {
        if (!isLoading) {
            viewModel.fetchSearchImage(
                query = query,
                isPageClear = isPageClear,
            )
        }
    }

    /**
     * 검색 페이지 호출
     */
    fun goSearchPage(str: String) {
        val searchStr = if (str == getString(R.string.feature_main_search_hint)) "" else str
        Intent(this, SearchActivity::class.java).apply {
            putExtra(KEY_SEARCH_STR, searchStr)
            activityResultLauncher.launch(this)
        }
    }

    /**
     * 상세 페이지 호출
     */
    fun goDetailPage(item: CommonSearchResultData.CommonSearchData) {
        Intent(this, DetailActivity::class.java).apply {
            putExtra(KEY_TITLE, item.title)
            putExtra(KEY_IMG_URL, item.imgUrl)
            putExtra(KEY_IS_FAVORITE, item.isFavorite)
            activityResultLauncher.launch(this)
        }
    }

}