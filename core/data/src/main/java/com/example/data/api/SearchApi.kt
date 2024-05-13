package com.example.data.api

import com.example.data.model.SearchImageResponse
import com.example.data.model.SearchVClipResponse
import com.example.data.utils.NetworkConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    /**
     * 이미지 검색하기
     */
    @GET(NetworkConfig.SEARCH_IMAGE_URL)
    suspend fun reqSearchImage(
        @Query("query") query: String,      // 검색을 원하는 질의어 : 필수
        @Query("sort") sort: String,        // 결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy
        @Query("page") page: Int,           // 결과 페이지 번호, 1~50 사이의 값, 기본 값 1
        @Query("size") size: Int,           // 한 페이지에 보여질 문서 수, 1~80 사이의 값, 기본 값 80
    ): Response<SearchImageResponse>

    /**
     * 동영상 검색하기
     */
    @GET(NetworkConfig.SEARCH_VCLIP_URL)
    suspend fun reqSearchVClip(
        @Query("query") query: String,      // 검색을 원하는 질의어 : 필수
        @Query("sort") sort: String,        // 결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy
        @Query("page") page: Int,           // 결과 페이지 번호, 1~50 사이의 값, 기본 값 1
        @Query("size") size: Int,           // 한 페이지에 보여질 문서 수, 1~80 사이의 값, 기본 값 80
    ): Response<SearchVClipResponse>
}