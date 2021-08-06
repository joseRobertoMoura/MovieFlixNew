package com.example.moviewflixnew.di

import com.example.moviewflixnew.utils.MovieFlixConstantes
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    fun provideApi() = Retrofit.Builder()
        .baseUrl(MovieFlixConstantes.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}