package com.example.moviewflixnew.usecase.favorites

import android.content.Context
import com.example.moviewflixnew.repository.favorites.MovieFlixFavoritesRepositoryImpl
import com.example.moviewflixnew.data.model.MoviesModel
import javax.inject.Inject

class FavoritesUseCaseImpl @Inject constructor(
    private val repository: MovieFlixFavoritesRepositoryImpl
    ) : FavoritesUseCase {

    override suspend fun favoritesUseCase(
        callbackSucess: (success: String) -> Unit,
        callbackError: (error: String) -> Unit,
        movie: MoviesModel,
        context: Context
    ) {
        repository.favoritesDataBase(callbackSucess,callbackError,movie,context)
    }

    override suspend fun favoritesListUseCase(
        callbackSucess: (success: MutableList<MoviesModel?>) -> Unit,
        callbackError: (error: String) -> Unit,
        context: Context
    ) {
        repository.getFavoritesDataBase(callbackSucess,callbackError,context)
    }

    override suspend fun favoritesDeleteUseCase(
        callbackSucess: (success: String) -> Unit,
        callbackError: (error: String) -> Unit,
        movie: MoviesModel,
        context: Context
    ) {
        repository.deleteDataFromDataBase(callbackSucess,callbackError,movie,context)
    }
}