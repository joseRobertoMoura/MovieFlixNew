package com.example.moviewflixnew.domain.usecase

import com.example.moviewflixnew.data.repository.MovieFlixRepositoryImp
import com.example.moviewflixnew.domain.model.toDetailModel
import com.example.moviewflixnew.ui.model.DetailModel

class GetDetailUseCaseImp(
    private val movieFlixRepository: MovieFlixRepositoryImp
):GetDetailUseCase {
    override suspend fun getDetail(id:String?):DetailModel = movieFlixRepository.requestMovieDetails(id)!!.toDetailModel()
}