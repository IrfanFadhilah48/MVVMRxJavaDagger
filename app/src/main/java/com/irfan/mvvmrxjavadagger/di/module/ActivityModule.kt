package com.irfan.mvvmrxjavadagger.di.module

import com.irfan.mvvmrxjavadagger.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

}