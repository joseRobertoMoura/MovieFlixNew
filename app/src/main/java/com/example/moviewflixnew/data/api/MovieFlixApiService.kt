package com.example.moviewflixnew.data.api

import com.example.moviewflixnew.model.DetailModel
import com.example.moviewflixnew.model.MoviesTendencyModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieFlixApiService {
    @GET("trending/movie/week?")
    fun getListTndency(@Query("api_key") key:String, @Query("page") numPage: String): Call<MoviesTendencyModel>

    @GET("movie/{id}")
    fun getMovieDetail(@Path("id") id: String?, @Query("api_key") chaveApi:String): Call<DetailModel>

}