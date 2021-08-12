package com.example.moviewflixnew.repository.updateUser

import android.content.Context
import com.example.moviewflixnew.R
import com.example.moviewflixnew.data.model.updateUser.UpdateUserModel
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import javax.inject.Inject

class UpdateUserImpl @Inject constructor() : UpdateUser {
    
    override suspend fun updateUser(
        callbackSucess: (success: String) -> Unit,
        callbackError: (error: String) -> Unit,
        updateUserModel: UpdateUserModel,
        context: Context
    ) {
        FirebaseAuth.getInstance()
            .sendPasswordResetEmail(updateUserModel.email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callbackSucess.invoke(context.getString(R.string.email_success_update_password))
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
                        callbackError.invoke(context.getString(R.string.erro_update_password))
                    }
                }
            }
    }

}