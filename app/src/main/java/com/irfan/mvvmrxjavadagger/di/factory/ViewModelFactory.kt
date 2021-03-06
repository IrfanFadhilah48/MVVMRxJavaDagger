package com.irfan.mvvmrxjavadagger.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Provides
import java.lang.Exception
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelFactory @Inject constructor(private val creators: MutableMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null){
            for (entry in creators){
                if (modelClass.isAssignableFrom(entry.key)){
                    creator = entry.value
                    break
                }
            }
        }
        if (creator == null){
            throw IllegalArgumentException("unknown model class $modelClass")
        }
        try {
            return creator.get() as T
        }
        catch (e: Exception){
            throw RuntimeException(e)
        }
    }

}