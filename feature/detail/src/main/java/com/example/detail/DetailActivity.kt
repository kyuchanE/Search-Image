package com.example.detail

import com.example.detail.databinding.ActivityDetailBinding
import com.example.ui.common.BaseActivity
import com.example.ui.utils.L
import com.example.ui.utils.loadRound
import com.example.ui.utils.loadUrl

class DetailActivity : BaseActivity<ActivityDetailBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_detail

    companion object {
        const val RESULT_CODE_DETAIL = 2
        const val KEY_POSITION = "KEY_POSITION"
        const val KEY_TITLE = "KEY_TITLE"
        const val KEY_IMG_URL = "KEY_IMG_URL"
        const val KEY_IS_FAVORITE = "KEY_IS_FAVORITE"
    }

    override fun initView() {
        getIntentData()
        initButtonClickListener()
    }

    private fun initButtonClickListener() {
        binding.btnBack.setOnClickListener { finish() }
        binding.btnFavorite.setOnClickListener {
            it.isSelected = !it.isSelected
        }
    }

    private fun getIntentData() {
        binding.strTitle = intent.getStringExtra(KEY_TITLE) ?: ""
        binding.btnFavorite.isSelected = intent.getBooleanExtra(KEY_IS_FAVORITE, false)
        binding.ivImg.loadUrl(intent.getStringExtra(KEY_IMG_URL) ?: "")
    }

}