package chanq.search_image.ui.main.fragment.result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import chanq.search_image.R
import chanq.search_image.databinding.ItemSearchResultListBinding
import com.example.domain.model.CommonSearchResultData
import com.example.domain.utils.compareFavorite
import com.example.ui.utils.loadRound
import com.example.ui.utils.setShowOrGone
import com.example.ui.utils.show
import com.example.ui.utils.toDate

class SearchResultListItemAdapter(
    searchResultList: MutableList<CommonSearchResultData.CommonSearchData>,
    private val searchResultClickEvent: (items: CommonSearchResultData.CommonSearchData) -> Unit = {_ ->},
    private var favoritesClickEvent: (items: CommonSearchResultData.CommonSearchData) -> Unit = { _ ->},
): RecyclerView.Adapter<SearchResultListItemAdapter.ResultItemViewHolder>() {
    private val itemList: MutableList<CommonSearchResultData.CommonSearchData> = mutableListOf()

    init {
        itemList.addAll(searchResultList)
    }

    inner class ResultItemViewHolder(
        itemView: View,
    ): RecyclerView.ViewHolder(itemView) {
        private val binding: ItemSearchResultListBinding? = DataBindingUtil.bind(itemView)

        fun bindView(position: Int) {
            binding?.let { b ->
                with(itemList[position]) {
                    b.isTypeImage = this.category != null
                    b.strUrl = this.url
                    b.strDateTime = this.datetime.toDate()
                    b.strCategory = this.category ?: ""
                    b.strTitle = this.title
                    b.cntPage = (this.page ?: 0).toString()
                    b.ivThumbnail.loadRound(this.imgUrl, 14)
                    b.llResultItem.setOnClickListener {
                        searchResultClickEvent(this)
                    }
                    b.btnFavorite.isSelected = this.isFavorite
                    b.btnFavorite.setOnClickListener {
                        it.isSelected = !it.isSelected
                        clickFavorite(position)
                    }

                    b.llBottom.setShowOrGone(this.isShowPage)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultItemViewHolder {
        return ResultItemViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_result_list, parent, false)
        )
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ResultItemViewHolder, position: Int) {
        holder.bindView(position)
    }

    fun addItemList(list: MutableList<CommonSearchResultData.CommonSearchData>) {
        val lastPos = itemList.size
        itemList.addAll(list)
        notifyItemRangeChanged(lastPos, itemList.lastIndex)
    }

    fun addItemListAfterClear(list: MutableList<CommonSearchResultData.CommonSearchData>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    fun clickFavorite(position: Int) {
        val item = itemList[position]
        itemList[position] = CommonSearchResultData.CommonSearchData(
            datetime = item.datetime,
            title = item.title,
            imgUrl = item.imgUrl,
            url = item.url,
            category = item.category,
            isFavorite = !item.isFavorite,
        )

        favoritesClickEvent(itemList[position])
    }

    fun changeFavorite(item: CommonSearchResultData.CommonSearchData) {
        var changePos: Int? = null
        itemList.forEachIndexed { index, commonSearchData ->
            if (commonSearchData.compareFavorite(item)) {
                changePos = index
            }
        }

        changePos?.let { pos ->
            itemList[pos] = item
            notifyItemChanged(pos)
        }

    }
}