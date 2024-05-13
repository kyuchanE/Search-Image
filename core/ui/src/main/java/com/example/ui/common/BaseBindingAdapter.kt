package com.example.ui.common

import android.view.View
import androidx.databinding.BindingAdapter

object BaseBindingAdapter {

    @BindingAdapter("setViewSelected")
    @JvmStatic
    fun View.setViewSelected(value: Boolean) {
        isSelected = value
    }

}