package com.example.moviewflixnew.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviewflixnew.data.model.login.LoginFireBaseModel
import com.example.moviewflixnew.usecase.login.LoginUseCase
import com.example.moviewflixnew.usecase.login.LoginUseCaseImpl
import kotlinx.coroutines.*
import javax.inject.Inject

sealed class LoginActionView{
    object LoginSuccess : LoginActionView()
    class LoginError(val error:String):LoginActionView()
}

class LoginViewModel @Inject constructor(
    private val useCase: LoginUseCaseImpl,
) : ViewModel() {

    private val mainDispatcher = Dispatchers.Main
    private val ioDispatcher = Dispatchers.IO
    private val job = SupervisorJob()
    private val mainDispatcherScope = CoroutineScope(mainDispatcher + job)
    private val ioDispatcherScope = CoroutineScope(ioDispatcher + job)

    private val _loginActionView by lazy { MutableLiveData<LoginActionView>() }
    val loginActionView: LiveData<LoginActionView>
        get() = _loginActionView


    fun init(login: LoginFireBaseModel) {
        executeLogin(login)
    }

    private fun executeLogin(login:LoginFireBaseModel) {
        mainDispatcherScope.launch {
            loginUseCase(login)
        }
    }

    private suspend fun loginUseCase(login:LoginFireBaseModel) {
        ioDispatcherScope.async {
            return@async useCase.executeLoginFireBase(login, ::success, ::error)
        }.await()
    }

    private fun success(){
        _loginActionView.postValue(LoginActionView.LoginSuccess)
        GlobalScope.coroutineContext.cancelChildren()
    }

    private fun error(error: String){
        _loginActionView.postValue( LoginActionView.LoginError(error))
        GlobalScope.coroutineContext.cancelChildren()
    }

}