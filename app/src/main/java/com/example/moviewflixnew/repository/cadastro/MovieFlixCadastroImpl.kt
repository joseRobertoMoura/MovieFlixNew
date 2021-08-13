package com.example.moviewflixnew.repository.cadastro

import android.content.Context
import android.provider.Settings.Global.getString
import android.view.View
import android.widget.Toast
import com.example.moviewflixnew.R
import com.example.moviewflixnew.data.model.cadastro.CadastroModel
import com.example.moviewflixnew.ui.model.MoviesModel
import com.example.moviewflixnew.ui.utils.preferences.ManagmentPreferences
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class MovieFlixCadastroImpl @Inject constructor() : MovieFlixCadastro {

    override suspend fun registerFireBase(
        callbackSucess: (success:String) -> Unit,
        callbackError: (error: String) -> Unit,
        cadastroModel: CadastroModel,
        context: Context
    ) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(cadastroModel.email!!, cadastroModel.password!!)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    FirebaseDatabase.getInstance().getReference()
                        .child("cadastro")
                        .child(cadastroModel.email.replace(".",""))
                        .setValue(cadastroModel)
                        .addOnCompleteListener {
                            if (it.isSuccessful){
                                callbackSucess.invoke("Sucesso Db")
                            }else{
                                callbackError.invoke("Error Db")
                            }
                        }.addOnFailureListener {
                            callbackError.invoke("Error Db")
                        }
                }
            }.addOnFailureListener {
                when (it) {
                    is FirebaseAuthWeakPasswordException -> {
                        callbackError.invoke(context.getString(R.string.msg_tamanho_senha))
                    }
                    is FirebaseAuthUserCollisionException -> {
                        callbackError.invoke(context.getString(R.string.msg_email_existente))
                    }
                    is FirebaseNetworkException -> {
                        callbackError.invoke(context.getString(R.string.msg_sem_conexao))
                    }
                    else -> {
                        callbackError.invoke(context.getString(R.string.msg_erro_cadastro_usuario))
                    }
                }
            }
    }

}