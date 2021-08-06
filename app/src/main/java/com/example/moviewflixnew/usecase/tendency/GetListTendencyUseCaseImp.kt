package com.example.moviewflixnew.usecase.tendency

import com.example.moviewflixnew.data.model.MoviesTendencyResponse
import com.example.moviewflixnew.repository.MovieFlixRepositoryImp
import javax.inject.Inject

class GetListTendencyUseCaseImp @Inject constructor(
    private val movieFlixRepository: MovieFlixRepositoryImp
): GetListTendencyUseCase {

    override fun getResponseMoviesTendency(
        numPage: String,
        callbackSuccess: (success: MoviesTendencyResponse) -> Unit,
        callbackError: (error: MoviesTendencyResponse) -> Unit
    ) = movieFlixRepository.requestMoviesTendency(numPage,callbackSuccess,callbackError)

}