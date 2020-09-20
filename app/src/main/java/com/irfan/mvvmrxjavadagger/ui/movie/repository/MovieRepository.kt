package com.irfan.mvvmrxjavadagger.ui.movie.repository

import androidx.lifecycle.MutableLiveData
import com.irfan.mvvmrxjavadagger.api.ApiServices
import com.irfan.mvvmrxjavadagger.di.module.ApiModule
import com.irfan.mvvmrxjavadagger.model.Movie
import com.irfan.mvvmrxjavadagger.model.ResponseMovie
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiServices: ApiServices) {

    fun getMoviePlaying(language: String?, page: Int): Single<ResponseMovie>{
        return apiServices.getMovie(language, page)
    }
}