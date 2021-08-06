package com.example.moviewflixnew.ui.di

import androidx.lifecycle.ViewModel
import com.example.moviewflixnew.di.ViewModelKey
import com.example.moviewflixnew.ui.viewModel.DetailMovieViewModel
import com.example.moviewflixnew.ui.viewModel.ListMoviesViewModel
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

}