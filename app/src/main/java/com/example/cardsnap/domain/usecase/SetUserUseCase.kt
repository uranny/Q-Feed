package com.example.cardsnap.domain.usecase

import com.example.cardsnap.data.repository.AuthRepository
import javax.inject.Inject

class SetUseUseCase @Inject constructor(
    val authRepository: AuthRepository
) {
}