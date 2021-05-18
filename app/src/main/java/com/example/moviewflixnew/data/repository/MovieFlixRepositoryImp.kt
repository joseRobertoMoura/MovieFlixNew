package com.example.moviewflixnew.data.repository

import com.example.moviewflixnew.data.model.MoviesTendencyResponse
import com.example.moviewflixnew.data.api.MovieFlixApi
import com.example.moviewflixnew.data.model.DetailResponse
import com.example.moviewflixnew.data.model.toDetailUseCase
import com.example.moviewflixnew.data.model.toMoviesModel
import com.example.moviewflixnew.domain.model.DetailUseCase
import com.example.moviewflixnew.domain.model.MoviesTendencyUseCase
import com.example.moviewflixnew.ui.model.DetailModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieFlixRepositoryImp(
    private var movieFlixApiTask:MovieFlixApi
): MovieFlixRepository{

    companion object {
        private const val REQUEST_SUCCESS = 200
    }

    override suspend fun requestMoviesTendency(
        numPage: String
    ):MoviesTendencyUseCase? {
        return  withContext(Dispatchers.IO){
            val request = movieFlixApiTask.retrofitApi()
                .getListTndency("579dbbdd2de6dd3cc42c4d65dc3afdae", numPage)
            if( request.clone().execute().code() == 200){
                request.clone().execute().body()!!.toMoviesModel()
            }else if(!request.clone().execute().isSuccessful){
                MoviesTendencyResponse(
                    result = null,
                    total_pages = null,
                    code = request.execute().code().toString()
                ).toMoviesModel()
            }else{
                MoviesTendencyResponse(
                    result = null,
                    total_pages = null,
                    code = request.execute().message().toString()
                ).toMoviesModel()
            }
        }
    }

    override suspend fun requestMovieDetails(
        id: String?
    ): DetailUseCase? {
        return  withContext(Dispatchers.IO){
            val requestDetail = movieFlixApiTask.retrofitApi()
                .getMovieDetail(id,"579dbbdd2de6dd3cc42c4d65dc3afdae")

            if (requestDetail.clone().execute().code() == 200){
                requestDetail.clone().execute().body()!!.toDetailUseCase()
            }else if(!requestDetail.clone().execute().isSuccessful){
                DetailResponse(
                    original_title = null,
                    poster_path = null,
                    overview = null,
                    original_name = null,
                    code = requestDetail.execute().code().toString()
                ).toDetailUseCase()
            }else{
                DetailResponse(
                    original_title = null,
                    poster_path = null,
                    overview = null,
                    original_name = null,
                    code = requestDetail.execute().message().toString()
                ).toDetailUseCase()
            }
        }

    }

}