package com.example.cardsnap.domain.usecase

import com.example.cardsnap.data.repository.AuthRepository
import com.example.cardsnap.data.repository.UserRepository
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
){

}