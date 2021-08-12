package com.example.moviewflixnew.usecase.updateUser

import android.content.Context
import com.example.moviewflixnew.data.model.updateUser.UpdateUserModel

interface UpdateUserUseCase {
    suspend fun updateUserUseCase(
        callbackSucess: (success:String) -> Unit,
        callbackError: (error:String) -> Unit,
        updateUserModel: UpdateUserModel,
        context: Context
    )
}