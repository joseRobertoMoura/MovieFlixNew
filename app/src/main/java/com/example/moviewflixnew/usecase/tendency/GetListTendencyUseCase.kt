package com.example.moviewflixnew.usecase.tendency

import com.example.moviewflixnew.data.model.MoviesTendencyResponse

interface GetListTendencyUseCase {
    suspend fun getResponseMoviesTendency(
        numPage: String,
        callbackSuccess: (success: MoviesTendencyResponse?) -> Unit,
        callbackError: (error: MoviesTendencyResponse?) -> Unit
    )
}