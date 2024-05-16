package chanq.search_image.ui.main.fragment.favorites

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import chanq.search_image.R
import chanq.search_image.databinding.FragmentFavoritesBinding
import chanq.search_image.ui.main.MainActivity
import chanq.search_image.ui.main.MainViewModel
import com.example.domain.model.CommonSearchResultData
import com.example.ui.common.BaseFragment
import com.example.ui.utils.L
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment: BaseFragment<FragmentFavoritesBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_favorites

    private val mainViewModel: MainViewModel by activityViewModels()
    private var favoriteAdapter: FavoritesAdapter? = null

    override fun initFragmentView() {
        L.d("initView")

        mainViewModel.getFavoritesList()
    }

    override fun initObserve() {
        super.initObserve()

        lifecycleScope.launch {
            mainViewModel.favoritesList.collect {
                initFavoritesList(it)
            }
        }
    }

    private fun initFavoritesList(list: MutableList<CommonSearchResultData.CommonSearchData>) {
        favoriteAdapter = FavoritesAdapter(
            favoritesList = list,
            favoriteItemClickEvent = { items ->
                (baseActivity as MainActivity).goDetailPage(items)
            },
            favoritesClickEvent = { items ->
                mainViewModel.clickFavorites(items)
            }
        )

        with(binding.rvFavorites) {
            isVisible = list.isNotEmpty()
            adapter = favoriteAdapter
            layoutManager = GridLayoutManager(baseActivity, 2)
        }

    }

    fun changeFavoriteItem(item: CommonSearchResultData.CommonSearchData) {
        favoriteAdapter?.changeFavorite(item)
    }
}