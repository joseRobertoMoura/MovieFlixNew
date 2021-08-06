package com.example.moviewflixnew.di.network.auth

import com.example.moviewflixnew.data.model.DetailResponse
import com.example.moviewflixnew.data.model.MoviesTendencyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthApi{
    @GET("trending/movie/week?")
    fun getListTndency(@Query("api_key") key:String, @Query("page") numPage: String): Call<MoviesTendencyResponse>

    @GET("movie/{id}")
    fun getMovieDetail(@Path("id") id: String?, @Query("api_key") chaveApi:String): Call<DetailResponse>
}