package com.example.moviewflixnew.di

import android.content.Context
import com.example.moviewflixnew.ui.di.MainComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module

@Component(modules = [
    RepositoryModule::class,
    ViewModelBuilderModule::class,
    UseCaseModule::class,
    SubComponentsModule::class,
    ApiModule::class,
    DialogModule::class
])
interface AppComponent {

    @Component.Factory
    interface factory {
        fun create(
            @BindsInstance applicationContext: Context
        ): AppComponent
    }

    fun mainComponent(): MainComponent.Factory
}

@Module(subcomponents = [
    MainComponent::class
])
object SubComponentsModule