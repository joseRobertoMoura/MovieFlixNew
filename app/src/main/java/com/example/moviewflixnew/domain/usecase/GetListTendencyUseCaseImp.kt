package com.example.moviewflixnew.domain.usecase

import com.example.moviewflixnew.data.repository.MovieFlixRepositoryImp
import com.example.moviewflixnew.domain.model.toMoviesModelUseCase
import com.example.moviewflixnew.ui.model.MoviesTendencyModel

class GetListTendencyUseCaseImp(
    private val movieFlixRepository: MovieFlixRepositoryImp
):GetListTendencyUseCase {

    override suspend fun getResponseMoviesTendency(numPage: String):MoviesTendencyModel{
        return movieFlixRepository.requestMoviesTendency(numPage)!!.toMoviesModelUseCase()
    }

}