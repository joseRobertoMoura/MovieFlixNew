package com.example.moviewflixnew.usecase.cadastro

import android.content.Context
import com.example.moviewflixnew.data.model.cadastro.CadastroModel
import com.example.moviewflixnew.repository.cadastro.MovieFlixCadastroImpl
import javax.inject.Inject

class CadastroUseCaseImpl @Inject constructor(
    val repository: MovieFlixCadastroImpl
) : CadastroUseCase{

    override suspend fun registerUseCase(
        callbackSucess: (success: String) -> Unit,
        callbackError: (error: String) -> Unit,
        cadastroModel: CadastroModel,
        context: Context
    ) {
        repository.registerFireBase(
            callbackSucess,
            callbackError,
            cadastroModel,
            context)
    }

}