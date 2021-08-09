package com.example.moviewflixnew.repository

import com.example.moviewflixnew.data.model.DetailResponse
import com.example.moviewflixnew.data.model.MoviesTendencyResponse
import com.example.moviewflixnew.di.network.auth.AuthApi
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class MovieFlixRepositoryImp @Inject constructor(
    private var movieFlixApiTask: AuthApi
): MovieFlixRepository{

    override suspend fun requestMoviesTendency(
        numPage: String,
        callbackSuccess:(success:MoviesTendencyResponse?) -> Unit,
        callbackError: (error:MoviesTendencyResponse?) -> Unit
    ){
        val request = movieFlixApiTask.getListTndency("579dbbdd2de6dd3cc42c4d65dc3afdae", numPage)
        request.enqueue(object: Callback<MoviesTendencyResponse>{
            override fun onResponse(
                call: Call<MoviesTendencyResponse>,
                response: Response<MoviesTendencyResponse>
            ) {
                callbackSuccess.invoke(
                    response.body()
                )
            }

            override fun onFailure(call: Call<MoviesTendencyResponse>, t: Throwable) {
               callbackError.invoke(
                   MoviesTendencyResponse(
                       null,
                       null,
                       t.message.toString()
                   )
               )
            }

        })
    }

    override suspend fun requestMovieDetails(
        id: String?,
        callbackSuccess:(success:DetailResponse?) -> Unit,
        callbackError: (error:String?) -> Unit
    ){
        val requestDetail = movieFlixApiTask.getMovieDetail(id, "579dbbdd2de6dd3cc42c4d65dc3afdae")
        requestDetail.enqueue(object : Callback<DetailResponse>{
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                callbackSuccess.invoke(
                    response.body()
                )
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                callbackError.invoke(
                    t.message.toString()
                )
            }
        })
    }
}