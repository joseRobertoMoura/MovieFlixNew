package com.example.moviewflixnew.data.repository

import com.example.moviewflixnew.data.model.DetailResponse
import com.example.moviewflixnew.domain.model.DetailUseCase
import com.example.moviewflixnew.domain.model.MoviesTendencyUseCase
import com.example.moviewflixnew.ui.model.DetailModel

interface MovieFlixRepository {

    suspend fun requestMoviesTendency(
        numPage: String
    ): MoviesTendencyUseCase?

    suspend fun requestMovieDetails(
        id: String?
    ):DetailUseCase?
}