package com.example.moviewflixnew.di

import com.example.moviewflixnew.repository.MovieFlixRepositoryImp
import com.example.moviewflixnew.usecase.details.GetDetailUseCaseImp
import com.example.moviewflixnew.usecase.tendency.GetListTendencyUseCaseImp
import com.example.moviewflixnew.ui.utils.MessagesDialogUtils
import com.example.moviewflixnew.ui.details.DetailMovieViewModel
import com.example.moviewflixnew.ui.listMovies.ListMoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    factory<MovieFlixRepositoryImp> { MovieFlixRepositoryImp(
        movieFlixApiTask = get()
    ) }
    factory<MessagesDialogUtils> { MessagesDialogUtils() }

    single { GetListTendencyUseCaseImp(
        movieFlixRepository = get()
    ) }

    single { GetDetailUseCaseImp(
        movieFlixRepository = get()
    ) }

    viewModel<ListMoviesViewModel> {
        ListMoviesViewModel(
            useCase = get()
        )
    }

    viewModel<DetailMovieViewModel> {
        DetailMovieViewModel(
            useCase = get()
        )
    }
}