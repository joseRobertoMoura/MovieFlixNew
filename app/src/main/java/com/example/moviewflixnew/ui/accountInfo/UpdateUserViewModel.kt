package com.example.moviewflixnew.ui.accountInfo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviewflixnew.data.model.updateUser.UpdateUserModel
import com.example.moviewflixnew.usecase.updateUser.UpdateUserUseCaseImpl
import kotlinx.coroutines.*
import javax.inject.Inject

sealed class UpdateUserActionView{
    class UpdateSuccess(val success:String) : UpdateUserActionView()
    class UpdateError(val error:String):UpdateUserActionView()
}

class UpdateUserViewModel @Inject constructor(
    val useCase: UpdateUserUseCaseImpl
) : ViewModel() {

    private val mainDispatcher = Dispatchers.Main
    private val ioDispatcher = Dispatchers.IO
    private val job = SupervisorJob()
    private val mainDispatcherScope = CoroutineScope(mainDispatcher + job)
    private val ioDispatcherScope = CoroutineScope(ioDispatcher + job)

    private val _updateUserActionView by lazy { MutableLiveData<UpdateUserActionView>() }
    val updateUserActionView: LiveData<UpdateUserActionView>
        get() = _updateUserActionView


    fun init(update: UpdateUserModel, context: Context) {
        executeUpdate(update, context)
    }

    private fun executeUpdate(update: UpdateUserModel, context: Context) {
        mainDispatcherScope.launch {
            updateUseCase(update, context)
        }
    }

    private suspend fun updateUseCase(update: UpdateUserModel, context: Context) {
        ioDispatcherScope.async {
            return@async useCase.updateUserUseCase(
                ::success,
                ::error,
                update,
                context
            )
        }.await()
    }

    private fun success(success: String){
        _updateUserActionView.postValue(UpdateUserActionView.UpdateSuccess(success))
        GlobalScope.coroutineContext.cancelChildren()
    }

    private fun error(error: String){
        _updateUserActionView.postValue( UpdateUserActionView.UpdateError(error))
        GlobalScope.coroutineContext.cancelChildren()
    }
}