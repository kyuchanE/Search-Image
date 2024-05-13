package com.example.main.activity

import android.animation.ObjectAnimator
import com.example.main.R
import com.example.main.databinding.ActivityMainBinding
import com.example.ui.common.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun init() {
        binding.isSelectedSearch = true

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