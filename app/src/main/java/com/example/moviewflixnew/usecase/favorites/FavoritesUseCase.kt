package com.example.moviewflixnew.usecase.favorites

import android.content.Context
import com.example.moviewflixnew.data.model.MoviesModel

interface FavoritesUseCase {
    suspend fun favoritesUseCase(
        callbackSucess: (success:String) -> Unit,
        callbackError: (error:String) -> Unit,
        movie: MoviesModel,
        context: Context
    )

    suspend fun favoritesListUseCase(
        callbackSucess: (success:MutableList<MoviesModel?>) -> Unit,
        callbackError: (error:String) -> Unit,
        context: Context
    )

    suspend fun favoritesDeleteUseCase(
        callbackSucess: (success:String) -> Unit,
        callbackError: (error:String) -> Unit,
        movie: MoviesModel,
        context: Context
    )
}