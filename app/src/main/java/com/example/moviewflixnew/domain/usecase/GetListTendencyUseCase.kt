package com.example.moviewflixnew.domain.usecase

import com.example.moviewflixnew.domain.model.MoviesTendencyUseCase
import com.example.moviewflixnew.ui.model.MoviesTendencyModel

interface GetListTendencyUseCase {
    fun getResponseMoviesTendency(
        numPage: String,
        callbackSuccess: (success: MoviesTendencyUseCase) -> Unit,
        callbackError: (error: MoviesTendencyUseCase) -> Unit
    )
}