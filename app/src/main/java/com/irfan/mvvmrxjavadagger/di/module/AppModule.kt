package com.irfan.mvvmrxjavadagger.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.irfan.mvvmrxjavadagger.BaseApp
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideSharedPreference(application: BaseApp): SharedPreferences = application.getSharedPreferences("testerShared", Context.MODE_PRIVATE)
}

//@Module
//abstract class AppModule {
//
//    @Binds
//    abstract fun provideContext(application: BaseApp) : Context
//
//    @Module
//    companion object {
//
//        @JvmStatic
//        @Provides
//        fun provideSharedPreferences(context: Context): SharedPreferences =
//            context.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE)
//    }
//}