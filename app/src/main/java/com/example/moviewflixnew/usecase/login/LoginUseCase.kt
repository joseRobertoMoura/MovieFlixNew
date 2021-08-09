package com.example.moviewflixnew.usecase.login

import com.example.moviewflixnew.data.model.login.LoginFireBaseModel

interface LoginUseCase {
    suspend fun executeLoginFireBase(
        dataLogin: LoginFireBaseModel,
        callbackSuccess:() -> Unit,
        callbackError: (error:String) -> Unit)
}