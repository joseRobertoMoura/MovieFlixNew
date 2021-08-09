package com.example.moviewflixnew.repository

import com.example.moviewflixnew.data.model.DetailResponse
import com.example.moviewflixnew.data.model.MoviesTendencyResponse

interface MovieFlixRepository {

    suspend fun requestMoviesTendency(
        numPage: String,
        callbackSuccess:(success:MoviesTendencyResponse?) -> Unit,
        callbackError: (error:MoviesTendencyResponse?) -> Unit
    )

    suspend fun requestMovieDetails(
        id: String?,
        callbackSuccess:(success:DetailResponse?) -> Unit,
        callbackError: (error:String?) -> Unit
    )
}