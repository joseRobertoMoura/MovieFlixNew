package com.example.moviewflixnew.usecase.login

import android.content.Context
import com.example.moviewflixnew.data.model.cadastro.CadastroModel
import com.example.moviewflixnew.data.model.login.LoginFireBaseModel

interface LoginUseCase {
    suspend fun executeLoginFireBase(
        dataLogin: LoginFireBaseModel,
        callbackSuccess:() -> Unit,
        callbackError: (error:String) -> Unit)

    suspend fun getInfoUser(
        callbackSucess: (success: CadastroModel?) -> Unit,
        callbackError: (error:String) -> Unit,
        email:String,
        context: Context
    )

}