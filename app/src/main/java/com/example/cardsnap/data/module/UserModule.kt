package com.example.cardsnap.data.module

import com.example.cardsnap.data.api.UserService
import com.example.cardsnap.data.repository.AuthRepository
import com.example.cardsnap.data.repository.UserRepository
import com.example.cardsnap.data.source.remote.UserRemoteDataSource
import com.example.cardsnap.data.source.base.UserDataSource
import com.example.cardsnap.data.usecase.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {
    @Provides
    @Singleton
    fun provideUserRemoteDataSource(
        authService : UserService
    ) : UserDataSource = UserRemoteDataSource(authService)

    @Provides
    @Singleton
    fun provideUserRepository(
        remoteDataSource : UserDataSource
    ) : UserRepository = UserRepository(remoteDataSource)

    @Provides
    @Singleton
    fun provideGetUserUseCase(
        authRepository: AuthRepository,
        userRepository: UserRepository
    ) : GetUserUseCase = GetUserUseCase(authRepository, userRepository)
}