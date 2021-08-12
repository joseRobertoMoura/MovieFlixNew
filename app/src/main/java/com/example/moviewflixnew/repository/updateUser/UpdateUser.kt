package com.example.moviewflixnew.repository.updateUser

import android.content.Context
import com.example.moviewflixnew.data.model.updateUser.UpdateUserModel

interface UpdateUser {
    suspend fun updateUser(
        callbackSucess: (success:String) -> Unit,
        callbackError: (error:String) -> Unit,
        updateUserModel: UpdateUserModel,
        context: Context
    )
}