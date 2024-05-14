package chanq.search_image.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import chanq.search_image.R
import chanq.search_image.databinding.ActivitySplashBinding
import com.example.main.activity.MainActivity
import com.example.ui.common.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_splash

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun initView() {

//        viewModel.fetchTest(
//            query = "고양이",
//            sort = "recency",
//            size = 10,
//            page = 1,
//        )

        goMain()
    }

    private fun goMain() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
        }
        finish()
    }
}