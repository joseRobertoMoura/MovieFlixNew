package com.example.moviewflixnew.repository.login

import android.content.Context
import com.example.moviewflixnew.data.model.cadastro.CadastroModel
import com.example.moviewflixnew.data.model.login.LoginFireBaseModel
import com.example.moviewflixnew.ui.model.MoviesModel

interface MovieFlixLogin {
    suspend fun loginFireBase(
        dataLogin: LoginFireBaseModel,
        callbackSuccess:() -> Unit,
        callbackError: (error:String) -> Unit)

    suspend fun getInfoRegisterDataBase(
        callbackSucess: (success:CadastroModel?) -> Unit,
        callbackError: (error:String) -> Unit,
        email:String,
        context: Context
    )

}