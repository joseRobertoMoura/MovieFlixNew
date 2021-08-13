package com.example.moviewflixnew.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviewflixnew.data.model.cadastro.CadastroModel
import com.example.moviewflixnew.data.model.login.LoginFireBaseModel
import com.example.moviewflixnew.usecase.login.LoginUseCase
import com.example.moviewflixnew.usecase.login.LoginUseCaseImpl
import kotlinx.coroutines.*
import javax.inject.Inject

sealed class LoginActionView{
    object LoginSuccess : LoginActionView()
    class LoginError(val error:String):LoginActionView()
}

sealed class LoginInfoActionView{
    class LoginInfoSuccess(val success: CadastroModel?) : LoginInfoActionView()
    class LoginInfoError(val error:String):LoginInfoActionView()
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

    private val _loginInfoActionView by lazy { MutableLiveData<LoginInfoActionView>() }
    val loginInfoActionView: LiveData<LoginInfoActionView>
        get() = _loginInfoActionView


    fun init(login: LoginFireBaseModel) {
        executeLogin(login)
    }

    fun initInfo(email: String,context: Context) {
        executeLoginInfo(email,context)
    }

    private fun executeLoginInfo(email: String,context: Context) {
        mainDispatcherScope.launch {
            loginInfoUseCase(email,context)
        }
    }

    private fun executeLogin(login:LoginFireBaseModel) {
        mainDispatcherScope.launch {
            loginUseCase(login)
        }
    }

    private suspend fun loginInfoUseCase(email: String,context: Context) {
        ioDispatcherScope.async {
            return@async useCase.getInfoUser(::successInfo, ::errorInfo, email,context)
        }.await()
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

    private fun successInfo(sucesso:CadastroModel?){
        _loginInfoActionView.postValue(LoginInfoActionView.LoginInfoSuccess(sucesso))
        GlobalScope.coroutineContext.cancelChildren()
    }

    private fun errorInfo(error: String){
        _loginInfoActionView.postValue( LoginInfoActionView.LoginInfoError(error))
        GlobalScope.coroutineContext.cancelChildren()
    }

}