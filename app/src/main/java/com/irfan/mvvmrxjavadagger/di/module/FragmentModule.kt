package com.irfan.mvvmrxjavadagger.di.module

import com.irfan.mvvmrxjavadagger.ui.tvseries.TvseriesFragment
import com.irfan.mvvmrxjavadagger.ui.movie.MovieFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): MovieFragment

    @ContributesAndroidInjector
    abstract fun contributeDashboardFragment(): TvseriesFragment
}