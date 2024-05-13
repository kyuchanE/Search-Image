package com.example.domain.repository

import com.example.domain.model.SearchImageData
import com.example.domain.model.SearchVClipData
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun reqSearchImageData(
        query: String,      // 검색을 원하는 질의어 : 필수
        sort: String,       // 결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy
        page: Int,          // 결과 페이지 번호, 1~50 사이의 값, 기본 값 1
        size: Int,          // 	한 페이지에 보여질 문서 수, 1~80 사이의 값, 기본 값 80
    ): Flow<SearchImageData?>

    fun reqSearchVClipData(
        query: String,      // 검색을 원하는 질의어 : 필수
        sort: String,       // 결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy
        page: Int,          // 결과 페이지 번호, 1~50 사이의 값, 기본 값 1
        size: Int,          // 	한 페이지에 보여질 문서 수, 1~80 사이의 값, 기본 값 80
    ): Flow<SearchVClipData?>
}