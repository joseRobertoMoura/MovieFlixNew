package com.example.moviewflixnew.di

import com.example.moviewflixnew.repository.MovieFlixRepository
import com.example.moviewflixnew.repository.MovieFlixRepositoryImp
import com.example.moviewflixnew.repository.login.MovieFlixLogin
import com.example.moviewflixnew.repository.login.MovieFlixLoginImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideMovieFlixRepository(repository: MovieFlixRepositoryImp): MovieFlixRepository

    @Singleton
    @Binds
    abstract fun provideLoginMovieFlixRepository(repository: MovieFlixLoginImpl): MovieFlixLogin
}