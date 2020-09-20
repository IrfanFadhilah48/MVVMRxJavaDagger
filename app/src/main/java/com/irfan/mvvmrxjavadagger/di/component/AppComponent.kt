package com.irfan.mvvmrxjavadagger.di.component

import com.irfan.mvvmrxjavadagger.BaseApp
import com.irfan.mvvmrxjavadagger.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApiModule::class,
    AndroidInjectionModule::class,
    ViewModelModule::class,
    ActivityModule::class,
    FragmentModule::class,
    AppModule::class
])
interface AppComponent {
    fun inject(app: BaseApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: BaseApp): Builder

        fun build(): AppComponent
    }
}