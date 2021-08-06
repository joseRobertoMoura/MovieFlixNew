package com.example.moviewflixnew.repository

import com.example.moviewflixnew.data.model.DetailResponse
import com.example.moviewflixnew.data.model.MoviesTendencyResponse

interface MovieFlixRepository {

    fun requestMoviesTendency(
        numPage: String,
        callbackSuccess:(success:MoviesTendencyResponse) -> Unit,
        callbackError: (error:MoviesTendencyResponse) -> Unit
    )

    fun requestMovieDetails(
        id: String?,
        callbackSuccess:(success:DetailResponse) -> Unit,
        callbackError: (error:String) -> Unit
    )
}