package com.example.moviewflixnew.usecase.details

import com.example.moviewflixnew.data.model.DetailResponse
import com.example.moviewflixnew.repository.MovieFlixRepositoryImp
import javax.inject.Inject

class GetDetailUseCaseImp @Inject constructor(
    private val movieFlixRepository: MovieFlixRepositoryImp
): GetDetailUseCase {
    override fun getDetail(
        id:String?,
        callbackSuccess:(success: DetailResponse) -> Unit,
        callbackError: (error: String) -> Unit
    ) = movieFlixRepository.requestMovieDetails(id,callbackSuccess,callbackError)
}