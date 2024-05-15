package com.example.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.io.InputStream
import java.util.concurrent.TimeUnit

@GlideModule
class OkHttpAppGlideModule: AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()

        val factory = OkHttpUrlLoader.Factory(client)

        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java, factory
        )
    }
}