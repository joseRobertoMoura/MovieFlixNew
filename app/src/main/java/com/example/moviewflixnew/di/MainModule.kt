package com.example.moviewflixnew.di

import com.example.moviewflixnew.data.api.MovieFlixApi
import com.example.moviewflixnew.data.repository.MovieFlixRepository
import com.example.moviewflixnew.viewModel.DetailMovieViewModel
import com.example.moviewflixnew.viewModel.ListMoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    factory<MovieFlixRepository> {
        MovieFlixRepository()
    }

    factory<MovieFlixApi> {
        MovieFlixApi()
    }

    viewModel<ListMoviesViewModel> {
        ListMoviesViewModel(
            repository = get()
        )
    }

    viewModel<DetailMovieViewModel> {
        DetailMovieViewModel(
            repository = get()
        )
    }
}