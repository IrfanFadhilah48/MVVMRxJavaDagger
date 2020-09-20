package com.irfan.mvvmrxjavadagger.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irfan.mvvmrxjavadagger.common.ResultState
import com.irfan.mvvmrxjavadagger.model.Movie
import com.irfan.mvvmrxjavadagger.model.ResponseMovie
import com.irfan.mvvmrxjavadagger.ui.movie.repository.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val _movie = MutableLiveData<ResultState<List<Movie>>>()
    val dataMovies: LiveData<ResultState<List<Movie>>>
        get() = _movie

    private fun setResultNowMovie(resultState: ResultState<List<Movie>>){
        _movie.postValue(resultState)
    }

    fun getMovie(language: String?, page: Int){
        setResultNowMovie(ResultState.loading())
        disposable.add(
            repository.getMoviePlaying(language, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseMovie>() {
                    override fun onSuccess(t: ResponseMovie) {
                        setResultNowMovie(ResultState.success(data = t.results))
                    }

                    override fun onError(e: Throwable) {
                        when(e){
                            is IOException -> setResultNowMovie(ResultState.error("No Internet Connection"))
                            is TimeoutException -> setResultNowMovie(ResultState.error("Request Timeout, pastikan internet anda stabil"))
                            is HttpException -> setResultNowMovie(ResultState.error(e.message().toString()))
                            else -> setResultNowMovie(ResultState.error("Terjadi Kesalahan"))
                        }
                    }
                })
        )

    }
}