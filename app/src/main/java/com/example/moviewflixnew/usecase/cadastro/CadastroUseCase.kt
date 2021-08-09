package com.example.moviewflixnew.usecase.cadastro

import android.content.Context
import com.example.moviewflixnew.data.model.cadastro.CadastroModel

interface CadastroUseCase {

    suspend fun registerUseCase(
        callbackSucess: (success:String) -> Unit,
        callbackError: (error:String) -> Unit,
        cadastroModel: CadastroModel,
        context: Context
    )
}