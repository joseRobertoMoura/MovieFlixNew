package com.example.moviewflixnew.di

import com.example.moviewflixnew.ui.utils.dialog.MessagesDialogUtils
import com.example.moviewflixnew.ui.utils.dialog.MessagesDialogUtilsImpl
import com.example.moviewflixnew.usecase.details.GetDetailUseCase
import com.example.moviewflixnew.usecase.details.GetDetailUseCaseImp
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DialogModule {
    @Singleton
    @Binds
    abstract fun provideMessagesDialog(dialog: MessagesDialogUtilsImpl): MessagesDialogUtils
}