package com.example.ui.common

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.ui.R

object BaseBindingAdapter {

    @BindingAdapter("setViewSelected")
    @JvmStatic
    fun View.setViewSelected(value: Boolean) {
        isSelected = value
    }

    @BindingAdapter("isVisible")
    @JvmStatic
    fun View.isVisible(value: Boolean) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

    @BindingAdapter("searchHintStr")
    @JvmStatic
    fun TextView.searchHintStr(str: String) {
        if(text.equals(str)) {
            setTextColor(ContextCompat.getColor(this.context, R.color.color_888888))
        } else {
            setTextColor(ContextCompat.getColor(this.context, R.color.color_222222))
        }
    }

    @BindingAdapter("isTypeImage")
    @JvmStatic
    fun ImageView.isTypeImage(isTypeImage: Boolean) {
        if (isTypeImage) {
            setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.icon_image))
        } else {
            setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.icon_video))
        }
    }

}