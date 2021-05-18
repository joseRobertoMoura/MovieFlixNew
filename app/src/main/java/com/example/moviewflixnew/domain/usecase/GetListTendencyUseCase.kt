package com.example.moviewflixnew.domain.usecase

import com.example.moviewflixnew.ui.model.MoviesTendencyModel

interface GetListTendencyUseCase {
    suspend fun getResponseMoviesTendency(numPage: String): MoviesTendencyModel
}