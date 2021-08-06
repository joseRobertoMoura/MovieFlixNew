package com.example.moviewflixnew.di

import com.example.moviewflixnew.usecase.details.GetDetailUseCase
import com.example.moviewflixnew.usecase.details.GetDetailUseCaseImp
import com.example.moviewflixnew.usecase.tendency.GetListTendencyUseCase
import com.example.moviewflixnew.usecase.tendency.GetListTendencyUseCaseImp
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