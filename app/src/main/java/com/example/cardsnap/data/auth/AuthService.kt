package com.example.cardsnap.data.auth

import com.example.cardsnap.data.auth.request.LoginRequest
import com.example.cardsnap.data.auth.request.RefreshRequest
import com.example.cardsnap.data.auth.request.RegisterRequest
import com.example.cardsnap.data.base.LoginResponse
import com.example.cardsnap.data.base.RefreshResponse
import com.example.cardsnap.data.base.RegisterResponse
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
        @Body authRegisterRequest : RegisterRequest
    ): Response<RegisterResponse>

    @POST("/auth/refresh")
    suspend fun refresh(
        @Body authRefreshRequest : RefreshRequest
    ): Response<RefreshResponse>
}