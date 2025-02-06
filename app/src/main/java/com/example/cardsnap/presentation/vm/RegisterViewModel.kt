package com.example.cardsnap.presentation.vm

import com.example.cardsnap.data.repository.AuthRepository
import com.example.cardsnap.data.repository.UserRepository
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val authRepository : AuthRepository,
    private val userRepository: UserRepository
) {

}