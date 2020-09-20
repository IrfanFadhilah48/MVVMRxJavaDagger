package com.irfan.mvvmrxjavadagger.ui.main.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irfan.mvvmrxjavadagger.common.ResultState
import com.irfan.mvvmrxjavadagger.ui.main.model.Genre
import com.irfan.mvvmrxjavadagger.ui.main.model.GenreResponse
import com.irfan.mvvmrxjavadagger.ui.main.repository.MainRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val _genre = MutableLiveData<ResultState<List<Genre>>>()
    val dataGenre: LiveData<ResultState<List<Genre>>>
        get() = _genre

    private fun setResultGenre(resultState: ResultState<List<Genre>>){
        _genre.postValue(resultState)
    }

    fun callGetGenre(language: String){
        setResultGenre(ResultState.loading())
        disposable.add(
            repository.callGenres(language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<GenreResponse>(){
                    override fun onSuccess(response: GenreResponse) {
                        setResultGenre(ResultState.success(response.genres))
                    }

                    override fun onError(e: Throwable) {
                        when(e){
                            is IOException -> setResultGenre(ResultState.error("No Internet Connection"))
                            is TimeoutException -> setResultGenre(ResultState.error("Request Timeout, pastikan internet anda stabil"))
                            is HttpException -> setResultGenre(ResultState.error(e.message().toString()))
                            else -> setResultGenre(ResultState.error("Terjadi Kesalahan"))
                        }
                    }
                })
        )
    }
}