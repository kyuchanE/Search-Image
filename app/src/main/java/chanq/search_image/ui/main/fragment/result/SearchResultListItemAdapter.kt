package chanq.search_image.ui.main.fragment.result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import chanq.search_image.R
import chanq.search_image.databinding.ItemSearchResultListBinding
import com.example.domain.model.CommonSearchResultData
import com.example.ui.utils.loadRound

class SearchResultListItemAdapter(
    searchResultList: MutableList<CommonSearchResultData.CommonSearchData>,
    private val searchResultClickEvent: (items: CommonSearchResultData.CommonSearchData) -> Unit = {_ ->},
    private val favoritesClickEvent: (items: CommonSearchResultData.CommonSearchData, itemList: MutableList<CommonSearchResultData.CommonSearchData>) -> Unit = {_, _ ->},
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
                    b.strDateTime = this.datetime
                    b.strCategory = this.category ?: ""
                    b.strTitle = this.title
                    b.ivThumbnail.loadRound(this.imgUrl, 14)
                    b.llResultItem.setOnClickListener {
                        searchResultClickEvent(this)
                    }
                    b.btnFavorite.isSelected = this.isFavorite
                    b.btnFavorite.setOnClickListener {
                        clickFavorite(position)
                    }
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
        notifyItemInserted(lastPos)
    }

    fun addItemListAfterClear(list: MutableList<CommonSearchResultData.CommonSearchData>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    private fun clickFavorite(position: Int) {
        val item = itemList[position]
        itemList[position] = CommonSearchResultData.CommonSearchData(
            datetime = item.datetime,
            title = item.title,
            imgUrl = item.imgUrl,
            url = item.url,
            category = item.category,
            isFavorite = true
        )

        favoritesClickEvent(
            itemList[position],
            itemList
        )
    }
}