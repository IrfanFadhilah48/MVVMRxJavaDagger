package com.irfan.mvvmrxjavadagger.api

import com.irfan.mvvmrxjavadagger.model.ResponseMovie
import com.irfan.mvvmrxjavadagger.ui.main.model.GenreResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices{

    @GET("movie/now_playing")
    fun getMovie(@Query("language") language: String?, @Query("page") page: Int): Single<ResponseMovie>

    @GET("genre/movie/list")
    fun getGenre(@Query("language") language: String?): Single<GenreResponse>
}