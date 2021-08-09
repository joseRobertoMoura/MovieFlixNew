package com.example.moviewflixnew.ui.di

import androidx.lifecycle.ViewModel
import com.example.moviewflixnew.di.ViewModelKey
import com.example.moviewflixnew.ui.cadastrar.CadastroViewModel
import com.example.moviewflixnew.ui.details.DetailMovieViewModel
import com.example.moviewflixnew.ui.listMovies.ListMoviesViewModel
import com.example.moviewflixnew.ui.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface SubMainModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListMoviesViewModel::class)
    fun bindListMoviesViewModel(viewModel: ListMoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailMovieViewModel::class)
    fun bindDetailMoviesViewModel(viewModel: DetailMovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CadastroViewModel::class)
    fun bindCadastroViewModel(viewModel: CadastroViewModel): ViewModel
}