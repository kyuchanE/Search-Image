package chanq.search_image.ui.main.fragment.result

import com.example.domain.model.CommonSearchResultData

sealed class SearchResultModel(open val id: Int) {

    companion object {
        const val TYPE_RESULT_LIST = 0
        const val TYPE_DIVIDER = 1
    }

    data class SearchResultList(
        override val id: Int = TYPE_RESULT_LIST,
        var itemList: MutableList<CommonSearchResultData.CommonSearchData> = mutableListOf(),
    ): SearchResultModel(id)

    data class SearchResultDivider(
        override val id: Int = TYPE_DIVIDER,
        var cntPage: String,
        var isShowLoading: Boolean = true,
    ): SearchResultModel(id)
}