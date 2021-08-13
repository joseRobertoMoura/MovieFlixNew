package com.example.moviewflixnew.repository.login

import android.content.Context
import com.example.moviewflixnew.data.model.cadastro.CadastroModel
import com.example.moviewflixnew.data.model.login.LoginFireBaseModel
import com.example.moviewflixnew.ui.model.MoviesModel
import com.example.moviewflixnew.ui.utils.preferences.ManagmentPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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

    override suspend fun getInfoRegisterDataBase(
        callbackSucess: (success: CadastroModel?) -> Unit,
        callbackError: (error: String) -> Unit,
        email: String,
        context: Context
    ) {
        FirebaseDatabase.getInstance().getReference("cadastro")
            .child(email.replace(".",""))
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val model = snapshot.getValue(CadastroModel::class.java)
                    callbackSucess.invoke(model)
                }
                override fun onCancelled(error: DatabaseError) {
                    callbackError.invoke(error.toString())
                }

            })
    }

}