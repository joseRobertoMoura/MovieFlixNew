package com.example.moviewflixnew.data.api

import com.example.moviewflixnew.data.model.DetailResponse
import com.example.moviewflixnew.data.model.MoviesTendencyResponse
import com.example.moviewflixnew.ui.model.DetailModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieFlixApiService {
    @GET("trending/movie/week?")
    fun getListTndency(@Query("api_key") key:String, @Query("page") numPage: String): Call<MoviesTendencyResponse>

    @GET("movie/{id}")
    fun getMovieDetail(@Path("id") id: String?, @Query("api_key") chaveApi:String): Call<DetailResponse>

}