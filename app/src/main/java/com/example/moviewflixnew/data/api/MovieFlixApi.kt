package com.example.moviewflixnew.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieFlixApi {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    private fun movieProvider(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun retrofitApi(): MovieFlixApiService = movieProvider().create(MovieFlixApiService::class.java)
}