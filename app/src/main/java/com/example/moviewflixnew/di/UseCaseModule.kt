package com.example.moviewflixnew.di

import com.example.moviewflixnew.data.repository.MovieFlixRepository
import com.example.moviewflixnew.data.repository.MovieFlixRepositoryImp
import com.example.moviewflixnew.domain.usecase.GetDetailUseCase
import com.example.moviewflixnew.domain.usecase.GetDetailUseCaseImp
import com.example.moviewflixnew.domain.usecase.GetListTendencyUseCase
import com.example.moviewflixnew.domain.usecase.GetListTendencyUseCaseImp
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class UseCaseModule {

    @Singleton
    @Binds
    abstract fun provideDetailUseCase(useCase: GetDetailUseCaseImp): GetDetailUseCase

    @Singleton
    @Binds
    abstract fun provideTendencyUseCase(repository: GetListTendencyUseCaseImp): GetListTendencyUseCase

}