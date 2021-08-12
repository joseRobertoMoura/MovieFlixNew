package com.example.moviewflixnew

import android.app.Application
import com.example.moviewflixnew.di.AppComponent
import com.example.moviewflixnew.di.DaggerAppComponent
import com.google.firebase.FirebaseApp

class MyApp:Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.factory().create(this)
        FirebaseApp.initializeApp(this)
    }
}