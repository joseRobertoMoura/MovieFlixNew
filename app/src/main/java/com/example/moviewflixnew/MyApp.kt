package com.example.moviewflixnew

import android.app.Application
import com.example.moviewflixnew.di.AppComponent
import com.example.moviewflixnew.di.DaggerAppComponent

class MyApp:Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

//        startKoin {
//            androidLogger(Level.NONE)
//            androidContext(this@MyApp)
//
//            modules(mainModule)
//        }

        component = DaggerAppComponent.factory().create(this)
    }
}