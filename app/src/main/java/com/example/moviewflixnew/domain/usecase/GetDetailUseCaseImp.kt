package com.example.moviewflixnew.domain.usecase

import android.content.Context
import android.location.Geocoder
import com.example.moviewflixnew.data.repository.MovieFlixRepositoryImp
import com.example.moviewflixnew.domain.model.DetailUseCase
import com.example.moviewflixnew.domain.model.toDetailModel
import com.example.moviewflixnew.ui.model.DetailModel
import javax.inject.Inject

class GetDetailUseCaseImp @Inject constructor(
    private val movieFlixRepository: MovieFlixRepositoryImp
):GetDetailUseCase {
    override fun getDetail(
        id:String?,
        callbackSuccess:(success: DetailUseCase) -> Unit,
        callbackError: (error: String) -> Unit
    ) = movieFlixRepository.requestMovieDetails(id,callbackSuccess,callbackError)
}