package com.example.moviewflixnew.ui.utils.preferences

import android.content.Context

class ManagmentPreferences(val context: Context) {

    companion object{
        const val KEY_USER_NAME = "UserName"
    }

    private val mSharedPreferences = AppPreferences(context)

    fun initializeSession(nome: String){
        mSharedPreferences.storeString(KEY_USER_NAME, nome)
    }

    fun finishSession(){
        mSharedPreferences.storeString(KEY_USER_NAME, "")
    }

    fun getInfoUser():String {
        return mSharedPreferences.getString(KEY_USER_NAME)
    }

}