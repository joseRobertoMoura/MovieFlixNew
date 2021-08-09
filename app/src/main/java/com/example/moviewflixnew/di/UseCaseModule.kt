package com.example.moviewflixnew.di

import com.example.moviewflixnew.usecase.cadastro.CadastroUseCase
import com.example.moviewflixnew.usecase.cadastro.CadastroUseCaseImpl
import com.example.moviewflixnew.usecase.details.GetDetailUseCase
import com.example.moviewflixnew.usecase.details.GetDetailUseCaseImp
import com.example.moviewflixnew.usecase.login.LoginUseCase
import com.example.moviewflixnew.usecase.login.LoginUseCaseImpl
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
    abstract fun provideTendencyUseCase(useCase: GetListTendencyUseCaseImp): GetListTendencyUseCase

    @Singleton
    @Binds
    abstract fun provideLoginUseCase(useCase: LoginUseCaseImpl) : LoginUseCase

    @Singleton
    @Binds
    abstract fun provideCadastroUseCase(useCase: CadastroUseCaseImpl) : CadastroUseCase
}