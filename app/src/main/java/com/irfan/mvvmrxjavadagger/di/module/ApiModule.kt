package com.irfan.mvvmrxjavadagger.di.module

import com.irfan.mvvmrxjavadagger.BuildConfig
import com.irfan.mvvmrxjavadagger.api.ApiServices
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule{

    @Provides
    @Singleton
    fun provideLogging(): HttpLoggingInterceptor {
        val logginInterceptor = HttpLoggingInterceptor()
        logginInterceptor.level = if (BuildConfig.DEBUG){
            HttpLoggingInterceptor.Level.BODY
        }
        else{
            HttpLoggingInterceptor.Level.NONE
        }
        return logginInterceptor
    }

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {
        var requestParams: Request
        val interceptor = Interceptor{chain ->
            val requestParam = chain.request()
            val url = requestParam.url()
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()
            requestParams = requestParam.newBuilder().url(url).build()
            chain.proceed(requestParams)
        }

        return OkHttpClient.Builder()
            .addNetworkInterceptor(provideLogging())
            .addInterceptor(interceptor)
            .retryOnConnectionFailure(false)
            .build()
    }

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL_API)
            .client(provideClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiServices(retrofit: Retrofit): ApiServices{
        return retrofit.create(ApiServices::class.java)
    }
    
}