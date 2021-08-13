package com.example.moviewflixnew.ui.utils.preferences

import android.content.Context

class ManagmentPreferences(val context: Context) {

    companion object{
        const val KEY_USER_NAME = "UserName"
        const val KEY_USER_EMAIL = "UserEmail"
    }

    private val mSharedPreferences = AppPreferences(context)

    fun initializeSession(email: String, nome:String){
        mSharedPreferences.storeString(KEY_USER_EMAIL, email)
        mSharedPreferences.storeString(KEY_USER_NAME, nome)
    }

    fun finishSession(){
        mSharedPreferences.storeString(KEY_USER_EMAIL, "")
    }

    fun getInfoUserName():String {
        return mSharedPreferences.getString(KEY_USER_NAME)
    }

    fun getInfoUserEmail():String {
        return mSharedPreferences.getString(KEY_USER_EMAIL)
    }

}