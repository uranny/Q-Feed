package com.example.cardsnap.data.api

import com.example.cardsnap.domain.entity.request.LoginRequest
import com.example.cardsnap.domain.entity.request.RefreshRequest
import com.example.cardsnap.domain.entity.request.RegisterRequest
import com.example.cardsnap.domain.entity.response.LoginResponse
import com.example.cardsnap.domain.entity.response.RefreshResponse
import com.example.cardsnap.domain.entity.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/login")
    suspend fun login(
        @Body loginRequest : LoginRequest
    ): Response<LoginResponse>

    @POST("/auth/register")
    suspend fun register(
        @Body registerRequest : RegisterRequest
    ): Response<RegisterResponse>

    @POST("/auth/refresh")
    suspend fun refresh(
        @Body refreshRequest : RefreshRequest
    ): Response<RefreshResponse>
}