package com.example.detail

import android.content.Intent
import androidx.activity.OnBackPressedCallback
import com.example.detail.databinding.ActivityDetailBinding
import com.example.ui.common.BaseActivity
import com.example.ui.utils.L
import com.example.ui.utils.loadRound
import com.example.ui.utils.loadUrl

class DetailActivity : BaseActivity<ActivityDetailBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_detail

    private var firstFavoriteState = false

    companion object {
        const val RESULT_CODE_DETAIL = 2
    }

    override fun initView() {
        getIntentData()
        initButtonClickListener()
        firstFavoriteState = DetailObject.getDetailData()?.isFavorite ?: false

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goMainPage()
            }
        })

    }

    private fun initButtonClickListener() {
        binding.btnBack.setOnClickListener { goMainPage() }
        binding.btnFavorite.setOnClickListener {
            it.isSelected = !it.isSelected
        }
    }

    private fun getIntentData() {
        binding.strTitle = DetailObject.getDetailData()?.title ?: ""
        binding.btnFavorite.isSelected = DetailObject.getDetailData()?.isFavorite ?: false
        binding.ivImg.loadUrl(DetailObject.getDetailData()?.imgUrl ?: "")
    }

    private fun goMainPage() {
        if (firstFavoriteState != binding.btnFavorite.isSelected) {
            DetailObject.changeFavorite(binding.btnFavorite.isSelected)
            setResult(RESULT_CODE_DETAIL)
        } else {
            DetailObject.clear()
        }
        finish()
    }

}