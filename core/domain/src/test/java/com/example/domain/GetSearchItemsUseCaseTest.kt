package com.example.domain

import com.example.domain.model.CommonSearchResultData
import com.example.domain.usecase.GetSearchItemsUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

class GetSearchItemsUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val searchRepository = TestSearchRepository()
    private val favoritesRepository = TestFavoritesRepository()

    val useCase = GetSearchItemsUseCase(
        searchRepository,
        favoritesRepository
    )

    @Test
    fun whenSearchItemIsEndPage_onlyVClipReturned() = runTest {
        val onlyVClipReturned = useCase(
            query = "test",
            page = 1,
            isImageResultPageEnd = true,
            isVClipResultPageEnd = false,
        )

        onlyVClipReturned.collectLatest {
            assertEquals(
                CommonSearchResultData(
                    data = mutableListOf(
                        CommonSearchResultData.CommonSearchData(
                            datetime = "datetime",
                            title = "title",
                            imgUrl = "thumbnail",
                            url = "url",
                            category = null,
                        )
                    ),
                    errorCode = null,
                    errorMsg = null,
                    isVClipResultPageEnd = false,
                    page = 1
                ),
                it
            )
        }
    }

    @Test
    fun whenVClipItemIsEndPage_onlySearchReturned() = runTest {
        val onlyVClipReturned = useCase(
            query = "test",
            page = 1,
            isImageResultPageEnd = false,
            isVClipResultPageEnd = true,
        )

        onlyVClipReturned.collectLatest {
            assertEquals(
                CommonSearchResultData(
                    data = mutableListOf(
                        CommonSearchResultData.CommonSearchData(
                            datetime = "datetime",
                            title = "sitename",
                            imgUrl = "thumbnail_url",
                            url = "doc_url",
                            category = "collection",
                        )
                    ),
                    errorCode = null,
                    errorMsg = null,
                    isVClipResultPageEnd = false,
                    page = 1
                ),
                it
            )
        }
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}