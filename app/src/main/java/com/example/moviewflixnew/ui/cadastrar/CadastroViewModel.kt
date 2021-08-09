package com.example.moviewflixnew.ui.cadastrar

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviewflixnew.data.model.cadastro.CadastroModel
import com.example.moviewflixnew.usecase.cadastro.CadastroUseCaseImpl
import kotlinx.coroutines.*
import javax.inject.Inject

sealed class CadastroActionView{
    class CadastroSuccess(val success:String) : CadastroActionView()
    class CadastroError(val error:String):CadastroActionView()
}

class CadastroViewModel @Inject constructor(
    val useCase: CadastroUseCaseImpl
) :ViewModel() {

    private val mainDispatcher = Dispatchers.Main
    private val ioDispatcher = Dispatchers.IO
    private val job = SupervisorJob()
    private val mainDispatcherScope = CoroutineScope(mainDispatcher + job)
    private val ioDispatcherScope = CoroutineScope(ioDispatcher + job)

    private val _cadastroActionView by lazy { MutableLiveData<CadastroActionView>() }
    val cadastroActionView: LiveData<CadastroActionView>
        get() = _cadastroActionView


    fun init(cadastro: CadastroModel, context: Context) {
        executeCadastro(cadastro, context)
    }

    private fun executeCadastro(cadastro: CadastroModel, context: Context) {
        mainDispatcherScope.launch {
            cadastroUseCase(cadastro, context)
        }
    }

    private suspend fun cadastroUseCase(cadastro: CadastroModel, context: Context) {
        ioDispatcherScope.async {
            return@async useCase.registerUseCase(
                ::success,
                ::error,
                cadastro,
                context
            )
        }.await()
    }

    private fun success(success: String){
        _cadastroActionView.postValue(CadastroActionView.CadastroSuccess(success))
        GlobalScope.coroutineContext.cancelChildren()
    }

    private fun error(error: String){
        _cadastroActionView.postValue( CadastroActionView.CadastroError(error))
        GlobalScope.coroutineContext.cancelChildren()
    }
}