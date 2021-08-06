package com.example.moviewflixnew.data.repository

import com.example.moviewflixnew.data.model.DetailResponse
import com.example.moviewflixnew.domain.model.DetailUseCase
import com.example.moviewflixnew.domain.model.MoviesTendencyUseCase
import com.example.moviewflixnew.ui.model.DetailModel

interface MovieFlixRepository {

    fun requestMoviesTendency(
        numPage: String,
        callbackSuccess:(success:MoviesTendencyUseCase) -> Unit,
        callbackError: (error:MoviesTendencyUseCase) -> Unit
    )

    fun requestMovieDetails(
        id: String?,
        callbackSuccess:(success:DetailUseCase) -> Unit,
        callbackError: (error:String) -> Unit
    )
}