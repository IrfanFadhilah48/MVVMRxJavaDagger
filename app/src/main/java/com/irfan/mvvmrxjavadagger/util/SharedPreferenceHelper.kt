package com.irfan.mvvmrxjavadagger.util

import android.content.SharedPreferences
import com.google.gson.Gson
import com.irfan.mvvmrxjavadagger.ui.main.model.Genre
import javax.inject.Inject

class SharedPreferenceHelper @Inject constructor(private val mSharedPrefs: SharedPreferences) {

    private val gson = Gson()

    fun putDataGenre(dataGenre: List<Genre>?){
        mSharedPrefs.edit().putString(Constant.DATA_GENRE, gson.toJson(dataGenre)).apply()
    }

    fun getDataGenre(): String? = mSharedPrefs.getString(Constant.DATA_GENRE, "")

}