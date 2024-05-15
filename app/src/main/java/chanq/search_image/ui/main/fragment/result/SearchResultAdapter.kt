package chanq.search_image.ui.main.fragment.result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import chanq.search_image.R
import chanq.search_image.databinding.ItemSearchResultDividerBinding
import chanq.search_image.databinding.ItemSearchResultRecyclerViewBinding
import com.example.domain.model.CommonSearchResultData

class SearchResultAdapter(
    searchResultModelList: MutableList<SearchResultModel>,
    private val searchResultClickEvent: (items: CommonSearchResultData.CommonSearchData) -> Unit = {_ ->},
    private val favoritesClickEvent: (items: CommonSearchResultData.CommonSearchData) -> Unit = {_ ->},
): RecyclerView.Adapter<SearchResultViewHolder>() {
    private val itemList: MutableList<SearchResultModel> = mutableListOf()

    init {
        itemList.addAll(searchResultModelList)
    }

    /**
     *  검색된 아이템 리스트 - RecyclerView
     */
    inner class ListViewHolder(
        itemView: View
    ): SearchResultViewHolder(itemView) {
        private val binding: ItemSearchResultRecyclerViewBinding? = DataBindingUtil.bind(itemView)
        private var listAdapter: SearchResultListItemAdapter? = null

        override fun bindView(position: Int) {
            super.bindView(position)

            binding?.let {
                if (itemList[position] is SearchResultModel.SearchResultList) {
                    listAdapter = SearchResultListItemAdapter(
                        searchResultList = (itemList[position] as SearchResultModel.SearchResultList).itemList,
                        searchResultClickEvent = { items -> searchResultClickEvent(items) },
                        favoritesClickEvent = { items, list ->
                            favoritesClickEvent(items)
                            changeFavorite(position, list)
                        },
                    )

                    it.rvPageList.adapter = listAdapter
                }
            }
        }
    }

    /**
     *  페이지 구분자 - Divider
     */
    inner class DividerViewHolder(
        itemView: View
    ): SearchResultViewHolder(itemView) {
        private val binding: ItemSearchResultDividerBinding? = DataBindingUtil.bind(itemView)

        override fun bindView(position: Int) {
            super.bindView(position)

            binding?.let {
                if (itemList[position] is SearchResultModel.SearchResultDivider){
                    it.cntPage = (itemList[position] as SearchResultModel.SearchResultDivider).cntPage
                    it.isShowLoading = (itemList[position] as SearchResultModel.SearchResultDivider).isShowLoading
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val holder: SearchResultViewHolder =
            when(viewType) {
                SearchResultModel.TYPE_RESULT_LIST -> {
                    ListViewHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_search_result_recycler_view, parent, false)
                    )
                }
                else -> {
                    DividerViewHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_search_result_divider, parent, false)
                    )
                }
            }

        return holder
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemViewType(position: Int): Int = itemList[position].id

    fun addItemList(list: MutableList<SearchResultModel>) {
        val lastPos = itemList.lastIndex
        itemList.addAll(list)
//        notifyItemInserted(lastPos)
        notifyItemRangeChanged(lastPos, itemList.lastIndex)
    }

    fun addItemListAfterClear(list: MutableList<SearchResultModel>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    fun changeFavorite(pos: Int, list: MutableList<CommonSearchResultData.CommonSearchData>) {
        itemList[pos] = SearchResultModel.SearchResultList(
            itemList = list
        )
        notifyItemChanged(pos)
    }

    fun finishedLoading() {
        if (itemList[itemList.lastIndex] is SearchResultModel.SearchResultDivider) {
            val last: SearchResultModel.SearchResultDivider = itemList[itemList.lastIndex] as SearchResultModel.SearchResultDivider
            itemList[itemList.lastIndex] = SearchResultModel.SearchResultDivider(
                cntPage = last.cntPage,
                isShowLoading = false,
            )
            notifyItemChanged(itemList.lastIndex)
        }
    }

}