package com.example.moviewflixnew.utils

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import java.lang.Exception

class StatusUserFireBase {

    private val mainDispatcher = Dispatchers.Main
    private val ioDispatcher = Dispatchers.IO
    private val job = SupervisorJob()
    private val mainDispatcherScope = CoroutineScope(mainDispatcher + job)
    private val ioDispatcherScope = CoroutineScope(ioDispatcher + job)

    fun logoutFireBase(
        callbackLogout:() -> Unit,
        callbackErrorLogout:(error:String) -> Unit
    ){
        try{
            mainDispatcherScope.launch {
                ioDispatcherScope.async {
                    return@async FirebaseAuth.getInstance().signOut()
                }.await()
            }
            callbackLogout.invoke()
        }catch(e: Exception){
            callbackErrorLogout.invoke(e.toString())
        }

    }

    fun verifySectionFireBase(
        callbackSuccessVerifySection: () -> Unit,
        callbackErrorVerifySection: () -> Unit
    ) {
        mainDispatcherScope.launch {
            ioDispatcherScope.async {
                val userLoged = FirebaseAuth.getInstance().currentUser
                if (userLoged != null){
                    callbackSuccessVerifySection.invoke()
                }else{
                    callbackErrorVerifySection.invoke()
                }
            }.await()
        }
    }
}