package com.example.moviewflixnew.repository

import com.example.moviewflixnew.data.model.DetailResponse
import com.example.moviewflixnew.data.model.MoviesTendencyResponse
import com.example.moviewflixnew.di.network.auth.AuthApi
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
        callbackSuccess:(success:MoviesTendencyResponse) -> Unit,
        callbackError: (error:MoviesTendencyResponse) -> Unit
    ){
        coroutineScope.launch {
            withContext(Dispatchers.Default){
                val request = movieFlixApiTask.getListTndency("579dbbdd2de6dd3cc42c4d65dc3afdae", numPage)
                if( request.clone().execute().code() == 200){
                   callbackSuccess.invoke(request.clone().execute().body()!!)
                }else if(!request.clone().execute().isSuccessful){
                    callbackError(MoviesTendencyResponse(
                        result = null,
                        total_pages = null,
                        code = request.execute().code().toString()
                    ))
                }else{
                    callbackError(MoviesTendencyResponse(
                        result = null,
                        total_pages = null,
                        code = request.execute().message().toString()
                    ))
                }
            }
        }
    }

    override fun requestMovieDetails(
        id: String?,
        callbackSuccess:(success:DetailResponse) -> Unit,
        callbackError: (error:String) -> Unit
    ){
        coroutineScope.launch {
            withContext(Dispatchers.Default) {
                val requestDetail = movieFlixApiTask.getMovieDetail(id, "579dbbdd2de6dd3cc42c4d65dc3afdae")
                if (requestDetail.clone().execute().code() == 200) {
                    callbackSuccess.invoke(requestDetail.clone().execute().body()!!)
                } else if (!requestDetail.clone().execute().isSuccessful) {
                    callbackError.invoke(requestDetail.execute().code().toString())
                } else {
                    callbackError.invoke(requestDetail.execute().code().toString())
                }
            }
        }
    }
}