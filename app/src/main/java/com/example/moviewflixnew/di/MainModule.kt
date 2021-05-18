package com.example.moviewflixnew.di

import com.example.moviewflixnew.data.api.MovieFlixApi
import com.example.moviewflixnew.data.repository.MovieFlixRepositoryImp
import com.example.moviewflixnew.domain.usecase.GetDetailUseCaseImp
import com.example.moviewflixnew.domain.usecase.GetListTendencyUseCaseImp
import com.example.moviewflixnew.ui.view.fragments.utils.MessagesDialogUtils
import com.example.moviewflixnew.ui.viewModel.DetailMovieViewModel
import com.example.moviewflixnew.ui.viewModel.ListMoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    factory<MovieFlixRepositoryImp> { MovieFlixRepositoryImp(
        movieFlixApiTask = get()
    ) }
    factory<MessagesDialogUtils> { MessagesDialogUtils() }
    factory<MovieFlixApi> { MovieFlixApi() }

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