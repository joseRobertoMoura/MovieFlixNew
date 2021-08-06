package com.example.moviewflixnew.domain.usecase

import com.example.moviewflixnew.data.repository.MovieFlixRepositoryImp
import com.example.moviewflixnew.domain.model.MoviesTendencyUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetListTendencyUseCaseImp @Inject constructor(
    private val movieFlixRepository: MovieFlixRepositoryImp
):GetListTendencyUseCase {

    override fun getResponseMoviesTendency(
        numPage: String,
        callbackSuccess: (success: MoviesTendencyUseCase) -> Unit,
        callbackError: (error: MoviesTendencyUseCase) -> Unit
    ) = movieFlixRepository.requestMoviesTendency(numPage,callbackSuccess,callbackError)

}