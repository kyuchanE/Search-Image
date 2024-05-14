package com.example.main.activity

import android.animation.ObjectAnimator
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.main.R
import com.example.main.databinding.ActivityMainBinding
import com.example.main.fragment.FavoritesFragment
import com.example.main.fragment.SearchResultFragment
import com.example.ui.common.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch

@AndroidEntryPoint
@WithFragmentBindings
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_main

    private lateinit var pageAdapter: MainPageAdapter

    private var isImageResultPageEnd: Boolean = false
    private var isVClipResultPageEnd: Boolean = false

    private val viewModel by viewModels<MainViewModel>()

    /** init Fragment **/
    private val fragmentList = mutableListOf(
        SearchResultFragment() as Fragment,
        FavoritesFragment() as Fragment,
    )

    override fun initView() {
        binding.isSelectedSearch = true

        initPageAdapter()
        initButtonClickListener()
        viewModel.fetchSearchImage(
            query = "고양이",
            sort = "recency",
            size = 10,
            page = 1,
        )
    }

    override fun initObserve() {
        super.initObserve()

        lifecycleScope.launch {
            viewModel.loadingController.collect {

            }
        }

        lifecycleScope.launch {
            viewModel.searchResult.collect { searchResultData ->
                isImageResultPageEnd = searchResultData.isImageResultPageEnd
                isVClipResultPageEnd = searchResultData.isVClipResultPageEnd
            }
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
//        hideSoftInput()

        if (!binding.btnTabSearch.isSelected && isSelectedSearch) {
            animTabBar(true)
        } else if (!binding.btnTabFavorites.isSelected && !isSelectedSearch) {
            animTabBar(false)
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

}