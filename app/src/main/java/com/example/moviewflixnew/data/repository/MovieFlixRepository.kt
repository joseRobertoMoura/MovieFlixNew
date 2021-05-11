package com.example.moviewflixnew.data.repository

import com.example.moviewflixnew.data.api.MovieFlixApi
import com.example.moviewflixnew.model.DetailModel
import com.example.moviewflixnew.model.MoviesTendencyModel
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieFlixRepository() {
    val movieFlixApiTask:MovieFlixApi by inject(MovieFlixApi::class.java)

    companion object {
        private const val REQUEST_SUCCESS = 200
    }

    fun requestMoviesTendency(
        numPage: String,
        onSuccess: (MoviesTendencyModel) -> Unit,
        onError: (Int?, String?) -> Unit
    ) {
        val request = movieFlixApiTask.retrofitApi()
            .getListTndency("579dbbdd2de6dd3cc42c4d65dc3afdae", numPage)

        request.enqueue(object : Callback<MoviesTendencyModel> {

            override fun onResponse(
                call: Call<MoviesTendencyModel>,
                response: Response<MoviesTendencyModel>
            ) {
                val responseCode = response.code()

                if (responseCode == REQUEST_SUCCESS) {
                    val movieList: MoviesTendencyModel = response.body()!!
                    onSuccess.invoke(movieList)
                } else {
                    onError.invoke(responseCode, null)
                }
            }

            override fun onFailure(call: Call<MoviesTendencyModel>, t: Throwable) {
                onError.invoke(null, t.toString())
            }

        })

    }

    fun requestMovieDetails(
        id: String?,
        onSuccess: (DetailModel) -> Unit,
        onError: (Int?, String?) -> Unit
    ) {
        val requestDetails = movieFlixApiTask.retrofitApi()
            .getMovieDetail(id, "579dbbdd2de6dd3cc42c4d65dc3afdae")
        requestDetails.enqueue(object : Callback<DetailModel> {
            override fun onResponse(call: Call<DetailModel>, response: Response<DetailModel>) {
                val responseCodeDetail = response.code()
                if (responseCodeDetail == REQUEST_SUCCESS) {
                    val movieDetail: DetailModel = response.body()!!
                    onSuccess.invoke(movieDetail)
                } else {
                    onError.invoke(responseCodeDetail, null)
                }
            }

            override fun onFailure(call: Call<DetailModel>, t: Throwable) {
                onError.invoke(null, t.toString())
            }

        })

    }
}