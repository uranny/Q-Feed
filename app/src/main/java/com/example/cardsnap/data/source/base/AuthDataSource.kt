package com.example.cardsnap.data.source.base

import com.example.cardsnap.data.request.LoginRequest
import com.example.cardsnap.data.request.RefreshRequest
import com.example.cardsnap.data.request.RegisterRequest
import com.example.cardsnap.data.response.LoginResponse
import com.example.cardsnap.data.response.RefreshResponse

interface AuthDataSource {
    suspend fun login(loginRequest: LoginRequest) : LoginResponse
    suspend fun register(registerRequest: RegisterRequest)
    suspend fun refresh(refreshRequest: RefreshRequest) : RefreshResponse
}