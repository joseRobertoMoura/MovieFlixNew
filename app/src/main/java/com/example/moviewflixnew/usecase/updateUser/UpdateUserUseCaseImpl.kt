package com.example.moviewflixnew.usecase.updateUser

import android.content.Context
import com.example.moviewflixnew.data.model.cadastro.CadastroModel
import com.example.moviewflixnew.data.model.updateUser.UpdateUserModel
import com.example.moviewflixnew.repository.cadastro.MovieFlixCadastroImpl
import com.example.moviewflixnew.repository.updateUser.UpdateUserImpl
import javax.inject.Inject

class UpdateUserUseCaseImpl @Inject constructor(
    val repository: UpdateUserImpl
) : UpdateUserUseCase {
    override suspend fun updateUserUseCase(
        callbackSucess: (success: String) -> Unit,
        callbackError: (error: String) -> Unit,
        updateUserModel: UpdateUserModel,
        context: Context
    ) {
        repository.updateUser(
            callbackSucess,
            callbackError,
            updateUserModel,
            context)
    }
}