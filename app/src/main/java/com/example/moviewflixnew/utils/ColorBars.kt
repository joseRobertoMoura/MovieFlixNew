package com.example.moviewflixnew.utils

import android.app.Activity
import android.view.Window
import androidx.core.content.ContextCompat
import com.example.moviewflixnew.R

class ColorBars {

    fun changeColorPrimary(activity: Activity){
        val window : Window = activity.window
        window.statusBarColor= ContextCompat.getColor(activity, R.color.dark_blue)
        window.navigationBarColor = ContextCompat.getColor(activity, R.color.dark_yellow_700)
    }

    fun changeColorSecondary(activity: Activity){
        val window : Window = activity.window
        window.statusBarColor= ContextCompat.getColor(activity, R.color.dark_blue)
        window.navigationBarColor = ContextCompat.getColor(activity, R.color.dark_blue)
    }

    fun changeColorFragmentResponse(activity: Activity){
        val window : Window = activity.window
        window.statusBarColor= ContextCompat.getColor(activity, R.color.grey)
        window.navigationBarColor = ContextCompat.getColor(activity, R.color.grey)
    }
}