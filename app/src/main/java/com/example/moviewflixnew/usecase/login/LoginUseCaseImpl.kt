package com.example.moviewflixnew.usecase.login

import com.example.moviewflixnew.data.model.login.LoginFireBaseModel
import com.example.moviewflixnew.repository.login.MovieFlixLogin
import com.example.moviewflixnew.repository.login.MovieFlixLoginImpl
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(private val repository:MovieFlixLoginImpl) : LoginUseCase{
    override suspend fun executeLoginFireBase(
        dataLogin: LoginFireBaseModel,
        callbackSuccess:() -> Unit,
        callbackError: (error:String) -> Unit){
        repository.loginFireBase(dataLogin,callbackSuccess,callbackError)
    }
}
