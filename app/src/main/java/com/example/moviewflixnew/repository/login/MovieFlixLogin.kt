package com.example.moviewflixnew.repository.login

import com.example.moviewflixnew.data.model.login.LoginFireBaseModel

interface MovieFlixLogin {
    suspend fun loginFireBase(
        dataLogin: LoginFireBaseModel,
        callbackSuccess:() -> Unit,
        callbackError: (error:String) -> Unit)
}