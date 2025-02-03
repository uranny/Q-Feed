package com.example.cardsnap.data.auth

import com.example.cardsnap.data.request.LoginRequest
import com.example.cardsnap.data.request.RefreshRequest
import com.example.cardsnap.data.request.RegisterRequest
import com.example.cardsnap.data.response.LoginResponse
import com.example.cardsnap.data.response.RefreshResponse
import com.example.cardsnap.data.response.RegisterResponse
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