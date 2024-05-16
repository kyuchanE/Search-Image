package chanq.search_image.ui.main.fragment.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import chanq.search_image.R
import chanq.search_image.databinding.ItemFavoritesBinding
import com.example.domain.model.CommonSearchResultData
import com.example.ui.utils.loadRound

class FavoritesAdapter(
    favoritesList: MutableList<CommonSearchResultData.CommonSearchData>,
    private val favoriteItemClickEvent: (items: CommonSearchResultData.CommonSearchData) -> Unit = {_ ->},
    private val favoritesClickEvent: (items: CommonSearchResultData.CommonSearchData) -> Unit = {_ -> },
): RecyclerView.Adapter<FavoritesAdapter.FavoriteItemViewHolder>() {
    private val itemList: MutableList<CommonSearchResultData.CommonSearchData> = mutableListOf()
    init {
       itemList.addAll(favoritesList)
        setHasStableIds(true)
    }

    inner class FavoriteItemViewHolder(
        itemView: View,
    ): RecyclerView.ViewHolder(itemView) {
        private val binding: ItemFavoritesBinding? = DataBindingUtil.bind(itemView)

        fun bindView(position: Int) {
            binding?.let { b ->
                with(itemList[position]) {
                    b.isTypeImage = this.category != null
                    b.strDateTime = this.datetime
                    b.strTitle = this.title
                    b.ivThumbnail.loadRound(this.imgUrl, 14)

                    b.btnFavorite.isSelected = this.isFavorite
                    b.btnFavorite.setOnClickListener {
                        it.isSelected = !it.isSelected
                        clickFavorite(position)
                    }
                    b.llContainerFavorite.setOnClickListener {
                        favoriteItemClickEvent(this)
                    }

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteItemViewHolder {
        return FavoriteItemViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorites, parent, false)
        )
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: FavoriteItemViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private fun clickFavorite(position: Int) {
        val item = itemList[position]
        itemList[position] = CommonSearchResultData.CommonSearchData(
            datetime = item.datetime,
            title = item.title,
            imgUrl = item.imgUrl,
            url = item.url,
            category = item.category,
            isFavorite = !item.isFavorite,
        )

        favoritesClickEvent(
            itemList[position]
        )

    }
}