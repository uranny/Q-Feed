package com.example.cardsnap.data.module

import com.example.cardsnap.data.repository.AuthRepository
import com.example.cardsnap.data.repository.UserRepository
import com.example.cardsnap.domain.usecase.GetArticlesUseCase
import com.example.cardsnap.domain.usecase.SetProfileUseCase
import com.example.cardsnap.domain.usecase.SetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetUserUseCase(
        authRepository: AuthRepository,
        userRepository: UserRepository
    ) : GetArticlesUseCase = GetArticlesUseCase(authRepository, userRepository)

    @Provides
    @Singleton
    fun provideSetProfileUseCase(
        authRepository: AuthRepository,
        userRepository: UserRepository
    ) : SetProfileUseCase = SetProfileUseCase(authRepository, userRepository)


    @Provides
    @Singleton
    fun provideSetUserUseCase(
        authRepository: AuthRepository
    ) : SetUserUseCase = SetUserUseCase(authRepository)
}