package com.example.ui.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.ui.utils.bind

abstract class BaseFragment<B: ViewDataBinding>: Fragment() {
    protected lateinit var binding: B

    abstract val layoutId: Int

    val baseActivity: BaseActivity<B>
        get() = activity as BaseActivity<B>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflater.bind(layoutId, container)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserve()
        initFragmentView()
    }

    abstract fun initFragmentView()

    open fun initObserve() {}

    /**
     * Show SoftInput
     */
    open fun showSoftInput() {
        val imm = baseActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(baseActivity.currentFocus, 0)
    }

    /**
     * Hide SoftInput
     */
    open fun hideSoftInput() {
        baseActivity.currentFocus?.let {
            val imm = baseActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}