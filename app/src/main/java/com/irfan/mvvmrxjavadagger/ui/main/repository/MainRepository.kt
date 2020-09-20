package com.irfan.mvvmrxjavadagger.ui.main.repository

import com.irfan.mvvmrxjavadagger.api.ApiServices
import com.irfan.mvvmrxjavadagger.ui.main.model.GenreResponse
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

class MainRepository @Inject constructor(private val apiServices: ApiServices){

    fun callGenres(language: String): Single<GenreResponse> = apiServices.getGenre(language)
}