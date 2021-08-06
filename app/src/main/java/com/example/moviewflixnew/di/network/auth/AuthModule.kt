package com.example.moviewflixnew.di.network.auth

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
class AuthModule {

    @Provides
    fun providesAuthApi(retrofit: Retrofit):AuthApi =
        retrofit.create(AuthApi::class.java)
}