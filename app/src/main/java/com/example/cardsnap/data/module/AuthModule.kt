package com.example.cardsnap.data.module

import com.example.cardsnap.data.api.AuthService
import com.example.cardsnap.data.repository.AuthRepository
import com.example.cardsnap.data.source.remote.AuthRemoteDataSource
import com.example.cardsnap.data.source.base.AuthDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(
        authService : AuthService
    ) : AuthDataSource = AuthRemoteDataSource(authService)

    @Provides
    @Singleton
    fun provideAuthRepository(
        remoteDataSource : AuthDataSource
    ) : AuthRepository = AuthRepository(remoteDataSource)
}