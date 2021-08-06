package com.example.moviewflixnew.ui.di

import com.example.moviewflixnew.di.network.auth.AuthModule
import com.example.moviewflixnew.ui.view.MainActivity
import com.example.moviewflixnew.ui.view.fragments.*
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [SubMainModule::class, AuthModule::class])
interface MainComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): MainComponent
    }

    fun inject(activity:MainActivity)
    fun inject(fragment:CadastrarFragment)
    fun inject(fragment:DetailMoviesFragment)
    fun inject(fragment:ListMoviesFragment)
    fun inject(fragment:LoginFragment)
    fun inject(fragment:MainFragment)
    fun inject(fragment:SearchFragment)
    fun inject(fragment:SplashScreen)

}