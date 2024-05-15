package com.example.search

import android.content.Intent
import android.view.inputmethod.EditorInfo
import com.example.search.databinding.ActivitySearchBinding
import com.example.ui.common.BaseActivity
import com.example.ui.utils.L

class SearchActivity : BaseActivity<ActivitySearchBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_search

    private var searchStr: String = ""

    companion object {
        const val KEY_SEARCH_STR = "KEY_SEARCH_STR"
        const val RESULT_CODE_SEARCH = 0
    }

    override fun initView() {
        getIntentData()
        initButtonClickListener()

        binding.etSearch.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                goMainPage()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun getIntentData() {
        searchStr = intent.getStringExtra(KEY_SEARCH_STR) ?: ""
        with(binding.etSearch) {
            showSoftInput()
            setText(searchStr)
            requestFocus()
        }
    }

    private fun initButtonClickListener() {
        binding.btnBack.setOnClickListener { finish() }
        binding.btnSearch.setOnClickListener { goMainPage() }
        binding.btnClear.setOnClickListener { binding.etSearch.setText("") }
    }

    private fun goMainPage() {
        setResult(
            RESULT_CODE_SEARCH,
            Intent(this, Class.forName("chanq.search_image.ui.main.MainActivity")).apply {
                putExtra(KEY_SEARCH_STR, binding.etSearch.text.toString())
            }
        )
        finish()
    }
}