package com.example.moviewflixnew.usecase.login

import android.content.Context
import com.example.moviewflixnew.data.model.cadastro.CadastroModel
import com.example.moviewflixnew.data.model.login.LoginFireBaseModel
import com.example.moviewflixnew.repository.login.MovieFlixLogin
import com.example.moviewflixnew.repository.login.MovieFlixLoginImpl
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val repository:MovieFlixLoginImpl
    ) : LoginUseCase{
    override suspend fun executeLoginFireBase(
        dataLogin: LoginFireBaseModel,
        callbackSuccess:() -> Unit,
        callbackError: (error:String) -> Unit){
        repository.loginFireBase(dataLogin,callbackSuccess,callbackError)
    }

    override suspend fun getInfoUser(
        callbackSucess: (success: CadastroModel?) -> Unit,
        callbackError: (error: String) -> Unit,
        email: String,
        context: Context
    ) {
        repository.getInfoRegisterDataBase(callbackSucess,callbackError,email,context)
    }
}
