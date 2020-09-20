package com.irfan.mvvmrxjavadagger.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.irfan.mvvmrxjavadagger.di.ViewModelKey
import com.irfan.mvvmrxjavadagger.di.factory.ViewModelFactory
import com.irfan.mvvmrxjavadagger.ui.main.viewmodel.MainViewModel
import com.irfan.mvvmrxjavadagger.ui.movie.MovieViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: MovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    abstract fun bindFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}