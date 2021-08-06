package com.example.moviewflixnew.data.repository

import com.example.moviewflixnew.data.model.MoviesTendencyResponse
import com.example.moviewflixnew.data.model.toDetailUseCase
import com.example.moviewflixnew.data.model.toMoviesModel
import com.example.moviewflixnew.di.network.auth.AuthApi
import com.example.moviewflixnew.domain.model.DetailUseCase
import com.example.moviewflixnew.domain.model.MoviesTendencyUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieFlixRepositoryImp @Inject constructor(
    private var movieFlixApiTask: AuthApi
): MovieFlixRepository{

    val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun requestMoviesTendency(
        numPage: String,
        callbackSuccess:(success:MoviesTendencyUseCase) -> Unit,
        callbackError: (error:MoviesTendencyUseCase) -> Unit
    ){
        coroutineScope.launch {
            withContext(Dispatchers.Default){
                val request = movieFlixApiTask.getListTndency("579dbbdd2de6dd3cc42c4d65dc3afdae", numPage)
                if( request.clone().execute().code() == 200){
                   callbackSuccess.invoke(request.clone().execute().body()!!.toMoviesModel())
                }else if(!request.clone().execute().isSuccessful){
                    callbackError(MoviesTendencyResponse(
                        result = null,
                        total_pages = null,
                        code = request.execute().code().toString()
                    ).toMoviesModel())
                }else{
                    callbackError(MoviesTendencyResponse(
                        result = null,
                        total_pages = null,
                        code = request.execute().message().toString()
                    ).toMoviesModel())
                }
            }
        }
    }

    override fun requestMovieDetails(
        id: String?,
        callbackSuccess:(success:DetailUseCase) -> Unit,
        callbackError: (error:String) -> Unit
    ){
        coroutineScope.launch {
            withContext(Dispatchers.Default) {
                val requestDetail = movieFlixApiTask.getMovieDetail(id, "579dbbdd2de6dd3cc42c4d65dc3afdae")
                if (requestDetail.clone().execute().code() == 200) {
                    callbackSuccess.invoke(requestDetail.clone().execute().body()!!.toDetailUseCase())
                } else if (!requestDetail.clone().execute().isSuccessful) {
                    callbackError.invoke(requestDetail.execute().code().toString())
                } else {
                    callbackError.invoke(requestDetail.execute().code().toString())
                }
            }
        }
    }
}