package com.example.data.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.domain.model.CommonSearchResultData
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.Json
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Type
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class PreferenceUtil @Inject constructor(
    @ApplicationContext context: Context
) {
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

    fun getFavoriteList(): MutableList<JsonObject> {
        val str: String = preferences.getString(KEY_FAVORITE, "") ?: ""
        val returnList = mutableListOf<JsonObject>()

        if (str.isNotEmpty()) {
            val list = JsonParser.parseString(str) as JsonArray
            list.forEach {
                returnList.add(it as JsonObject)
            }
        }
        return returnList
    }

    fun setFavoriteList(item: CommonSearchResultData.CommonSearchData) {
        val tempArray =ArrayList<CommonSearchResultData.CommonSearchData>()
        val groupListType: Type = object : TypeToken<ArrayList<CommonSearchResultData.CommonSearchData?>?>() {}.type

        val prev = getString(KEY_FAVORITE,"")

        if(prev!=""){
            if(prev!="[]" || prev!="") tempArray.addAll(GsonBuilder().create().fromJson(prev, groupListType))
        }
        tempArray.add(item)
        val strList = GsonBuilder().create().toJson(tempArray, groupListType)
        preferences.edit().putString(KEY_FAVORITE, strList).apply()
    }

    fun removeFavoriteList() {
        preferences.edit().remove(KEY_FAVORITE).commit()
    }
}