package chanq.search_image.ui.main.fragment

import chanq.search_image.R
import chanq.search_image.databinding.FragmentFavoritesBinding
import com.example.ui.common.BaseFragment
import com.example.ui.utils.L
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment: BaseFragment<FragmentFavoritesBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_favorites

    override fun initFragmentView() {
        L.d("initView")
    }
}