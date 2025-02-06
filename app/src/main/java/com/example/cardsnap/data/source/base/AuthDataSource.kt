package com.example.cardsnap.data.source.base

import com.example.cardsnap.domain.entity.request.LoginRequest
import com.example.cardsnap.domain.entity.request.RefreshRequest
import com.example.cardsnap.domain.entity.request.RegisterRequest
import com.example.cardsnap.domain.entity.response.LoginResponse
import com.example.cardsnap.domain.entity.response.RefreshResponse

interface AuthDataSource {
    suspend fun login(loginRequest: LoginRequest) : LoginResponse
    suspend fun register(registerRequest: RegisterRequest)
    suspend fun refresh(refreshRequest: RefreshRequest) : RefreshResponse
}