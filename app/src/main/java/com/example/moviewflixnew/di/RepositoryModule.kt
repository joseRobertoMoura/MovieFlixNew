package com.example.moviewflixnew.di

import com.example.moviewflixnew.data.repository.MovieFlixRepository
import com.example.moviewflixnew.data.repository.MovieFlixRepositoryImp
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideMovieFlixRepository(repository: MovieFlixRepositoryImp): MovieFlixRepository

}