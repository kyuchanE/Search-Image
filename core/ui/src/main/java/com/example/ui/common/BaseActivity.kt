package com.example.ui.common

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.example.ui.R
import com.example.ui.databinding.LoadingBinding
import com.example.ui.utils.bind
import com.example.ui.utils.bindView
import com.example.ui.utils.gone
import com.example.ui.utils.show
import java.util.concurrent.atomic.AtomicInteger

abstract class BaseActivity<B: ViewDataBinding>: AppCompatActivity() {

    /**
     * view data binding
     */
    protected lateinit var binding: B
        private set

    /**
     * data binding layoutId
     */
    abstract val layoutId: Int

    /** loading **/
    private lateinit var loadingBinding: LoadingBinding

    /** 로딩 뷰 사용 카운트 **/
    private val loadingCount = AtomicInteger()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = bind(layoutId)
        binding.lifecycleOwner = this

        loadingBinding = bindView(R.layout.loading)
        (binding.root as ViewGroup).addView(
            loadingBinding.root,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        initObserve()
        initView()
    }

    abstract fun initView()

    open fun initObserve() {}

    /**
     * Show SoftInput
     */
    open fun showSoftInput() {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(currentFocus, 0)
    }

    /**
     * Hide SoftInput
     */
    open fun hideSoftInput() {
        currentFocus?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    open fun hideSoftInput(v: View? = null) {
        currentFocus?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    /**
     * show loading
     */
    fun showLoading() {
        runOnUiThread {
            if (!loadingBinding.root.isShown)
                loadingBinding.root.show()
        }
    }

    /**
     * hide loading
     */
    fun hideLoading() {
        runOnUiThread {
            if (loadingBinding.root.isShown)
                loadingBinding.root.gone()
        }
    }

}