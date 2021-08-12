package com.example.moviewflixnew.repository.favorites

import android.content.Context
import com.example.moviewflixnew.data.model.cadastro.CadastroModel
import com.example.moviewflixnew.ui.model.MoviesModel

interface MovieFlixFavoritesRepository {
    suspend fun favoritesDataBase(
        callbackSucess: (success:String) -> Unit,
        callbackError: (error:String) -> Unit,
        movie: MoviesModel,
        context: Context
    )

    suspend fun getFavoritesDataBase(
        callbackSucess: (success:MutableList<MoviesModel?>) -> Unit,
        callbackError: (error:String) -> Unit,
        context: Context
    )

    suspend fun deleteDataFromDataBase(
        callbackSucess: (success: String) -> Unit,
        callbackError: (error: String) -> Unit,
        movie: MoviesModel,
        context: Context
    )

}