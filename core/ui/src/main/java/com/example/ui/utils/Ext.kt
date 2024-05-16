package com.example.ui.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.di.GlideApp
import com.example.ui.R
import com.google.gson.JsonObject
import java.util.Locale

////////////////////////////// DataBinding //////////////////////////////

val Context.layoutInflater: LayoutInflater get() = LayoutInflater.from(this)

fun <T : ViewDataBinding> LayoutInflater.bind(layoutId: Int, parent: ViewGroup? = null, attachToParent: Boolean = false): T {
    return DataBindingUtil.inflate(this, layoutId, parent, attachToParent)
}

fun <T : ViewDataBinding> Activity.bind(layoutId: Int): T {
    return DataBindingUtil.setContentView(this, layoutId)
}

fun <T : ViewDataBinding> Activity.bindView(layoutId: Int, parent: ViewGroup? = null, attachToRoot: Boolean = false): T {
    return DataBindingUtil.inflate(layoutInflater, layoutId, parent, attachToRoot)
}

fun <T : ViewDataBinding> Fragment.bindView(layoutId: Int, parent: ViewGroup? = null, attachToRoot: Boolean = false): T {
    return DataBindingUtil.inflate(layoutInflater, layoutId, parent, attachToRoot)
}

////////////////////////////// View //////////////////////////////

fun View.show(): View {
    visibility = View.VISIBLE
    return this
}

fun View.hide(): View {
    visibility = View.INVISIBLE
    return this
}

fun View.gone(): View {
    visibility = View.GONE
    return this
}

fun View.setShowOrGone(isShow: Boolean): View {
    visibility = if (isShow) {
        View.VISIBLE
    } else {
        View.GONE
    }
    return this
}

////////////////////////////// ImageView //////////////////////////////

fun ImageView.loadUrl(url: String): ImageView {
    GlideApp.with(context)
        .load(url)
        .error(R.drawable.bg_error_img_load)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
    return this
}


fun ImageView.loadRound(url: String, round: Int): ImageView {
    GlideApp.with(context)
        .load(url)
        .error(R.drawable.bg_error_img_load)
        .transform(CenterCrop(), RoundedCorners(round.dp2px))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
    return this
}

////////////////////////////// Int //////////////////////////////

val Int.digit get() = if (this < 10) "0${toString()}" else toString()
val Int.px2dp get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.dp2px get() = (this * Resources.getSystem().displayMetrics.density).toInt()
val Int.boolean get() = this > 0
val Int.count get() = String.format(Locale.KOREA, "%,d", this)
