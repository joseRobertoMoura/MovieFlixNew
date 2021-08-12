package com.example.moviewflixnew.ui.di

import com.example.moviewflixnew.di.network.auth.AuthModule
import com.example.moviewflixnew.ui.SplashScreen
import com.example.moviewflixnew.ui.MainActivity
import com.example.moviewflixnew.ui.accountInfo.AccounInfoFragment
import com.example.moviewflixnew.ui.cadastrar.CadastrarFragment
import com.example.moviewflixnew.ui.details.DetailMoviesFragment
import com.example.moviewflixnew.ui.favorites.FavoritesFragment
import com.example.moviewflixnew.ui.listMovies.ListMoviesFragment
import com.example.moviewflixnew.ui.login.LoginFragment
import com.example.moviewflixnew.ui.main.MainFragment
import com.example.moviewflixnew.ui.search.SearchFragment
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [SubMainModule::class, AuthModule::class])
interface MainComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): MainComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: CadastrarFragment)
    fun inject(fragment: DetailMoviesFragment)
    fun inject(fragment: ListMoviesFragment)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: MainFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: SplashScreen)
    fun inject(fragment: AccounInfoFragment)
    fun inject(fragment: FavoritesFragment)

}