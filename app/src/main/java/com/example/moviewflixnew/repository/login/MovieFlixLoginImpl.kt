package com.example.moviewflixnew.repository.login

import com.example.moviewflixnew.data.model.login.LoginFireBaseModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import javax.inject.Inject

class MovieFlixLoginImpl @Inject constructor() : MovieFlixLogin {

    override suspend fun loginFireBase(
        dataLogin: LoginFireBaseModel,
        callbackSuccess: () -> Unit,
        callbackError: (error: String) -> Unit
    ) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(dataLogin.userName, dataLogin.password)
            .addOnCompleteListener {
                when{
                    it.isSuccessful -> {
                        callbackSuccess.invoke()
                    }
                    else -> {
                        callbackError.invoke("Error!")
                    }
                }
            }.addOnFailureListener {
                when (it) {
                    is FirebaseAuthWeakPasswordException -> {
                        callbackError.invoke("Senha incorreta!")
                    }
                    else -> {
                        callbackError.invoke("Erro ao efetuar o login")
                    }
                }
            }
    }

}