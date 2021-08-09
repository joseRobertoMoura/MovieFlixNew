package com.example.moviewflixnew.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviewflixnew.MyApp
import com.example.moviewflixnew.R
import com.example.moviewflixnew.di.AppComponent
import com.example.moviewflixnew.ui.di.MainComponent


class MainActivity : AppCompatActivity() {

    lateinit var mainComponent: MainComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        mainComponent = (applicationContext as MyApp).component.mainComponent().create()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {

    }


}