package com.example.main.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.CommonSearchResultData
import com.example.main.R
import com.example.main.databinding.ItemSearchResultListBinding

class SearchResultAdapter(
    searchResultList: MutableList<CommonSearchResultData.CommonSearchData>,
    private val searchResultClickEvent: (items: CommonSearchResultData.CommonSearchData) -> Unit = {_ ->},
): RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {
    private val itemList: MutableList<CommonSearchResultData.CommonSearchData> = mutableListOf()

    init {
        itemList.addAll(searchResultList)
    }

    inner class SearchResultViewHolder(
        itemView: View,
    ): RecyclerView.ViewHolder(itemView) {
        private val binding: ItemSearchResultListBinding? = DataBindingUtil.bind(itemView)

        fun bindView(position: Int) {
            binding?.let { b ->
                b.isFirstItem = position == 0
                if (position != 0) {
                    with(itemList[position - 1]) {
                        b.isTypeImage = this.category != null
                        b.strUrl = this.url
                        b.strDateTime = this.datetime
                        b.strCategory = this.category ?: ""
                        b.strTitle = this.title
                        b.llResultItem.setOnClickListener {
                            searchResultClickEvent(this)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_result_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return if (itemList.size == 0) { 0 } else { itemList.size + 1 }
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
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
}