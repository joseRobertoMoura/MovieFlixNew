package com.example.moviewflixnew.di

import com.example.moviewflixnew.BuildConfig
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    companion object{
        const val URL = BuildConfig.BASE_URL
    }


    @Provides
    fun provideApi() = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}