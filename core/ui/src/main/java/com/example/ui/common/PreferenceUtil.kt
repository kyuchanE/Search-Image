package com.example.ui.common

import android.content.Context
import android.content.SharedPreferences
import com.example.domain.model.CommonSearchResultData
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class PreferenceUtil(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    companion object {
        const val KEY_FAVORITE = "MY_FAVORITE"
    }

    fun getString(key: String, defValue: String): String{
        return preferences.getString(key,defValue).toString()
    }

    fun setString(key: String, defValue: String){
        preferences.edit().putString(key, defValue).apply()
    }

    fun setFavoriteList(item: CommonSearchResultData.CommonSearchData) {
        val tempArray =ArrayList<CommonSearchResultData.CommonSearchData>()
        val groupListType: Type = object : TypeToken<ArrayList<CommonSearchResultData.CommonSearchData?>?>() {}.type

        val prev = getString(KEY_FAVORITE,"")
        val convertedData = GsonBuilder().create().toJson(prev)

        if(prev!=""){
            if(prev!="[]" || prev!="") tempArray.addAll(GsonBuilder().create().fromJson(prev, groupListType))
        }
        tempArray.add(item)
        val strList = GsonBuilder().create().toJson(tempArray, groupListType)
        preferences.edit().putString(KEY_FAVORITE, strList).apply()
    }
}