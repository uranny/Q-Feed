package com.example.cardsnap.domain.usecase

import com.example.cardsnap.data.repository.AuthRepository
import com.example.cardsnap.data.repository.UserRepository
import javax.inject.Inject

// SetProfile, RefreshToken
class SetProfileUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
){
}