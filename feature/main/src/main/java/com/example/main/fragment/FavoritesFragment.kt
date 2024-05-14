package com.example.main.fragment

import com.example.main.R
import com.example.main.databinding.FragmentFavoritesBinding
import com.example.ui.common.BaseFragment
import com.example.ui.utils.L
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment: BaseFragment<FragmentFavoritesBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_favorites

    override fun initView() {
        L.d("initView")
    }
}