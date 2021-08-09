package com.example.moviewflixnew.repository.cadastro

import android.content.Context
import com.example.moviewflixnew.data.model.cadastro.CadastroModel

interface MovieFlixCadastro {
    suspend fun registerFireBase(
        callbackSucess: (success:String) -> Unit,
        callbackError: (error:String) -> Unit,
        cadastroModel: CadastroModel,
        context: Context
    )
}