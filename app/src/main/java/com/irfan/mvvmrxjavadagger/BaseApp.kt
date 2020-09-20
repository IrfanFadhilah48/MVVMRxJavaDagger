package com.irfan.mvvmrxjavadagger

import android.app.Application
import com.irfan.mvvmrxjavadagger.di.component.DaggerAppComponent
import com.irfan.mvvmrxjavadagger.di.module.AppModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class BaseApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>


    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
//            .appModule(AppModule(this))
            .build()
            .inject(this)

    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}