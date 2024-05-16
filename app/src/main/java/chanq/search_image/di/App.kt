package chanq.search_image.di

import android.app.Application
import com.example.data.utils.PreferenceUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    companion object{
        lateinit var preferences: PreferenceUtil
    }

    override fun onCreate() {
        super.onCreate()
        preferences = PreferenceUtil(applicationContext)
    }

}