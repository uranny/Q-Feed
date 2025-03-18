package com.example.cardsnap.domain.usecase

import com.example.cardsnap.data.repository.AuthRepository
import javax.inject.Inject

// Login, Register
class SetUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
}