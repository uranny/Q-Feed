package com.example.cardsnap.data.auth

import android.telecom.Call
import com.example.cardsnap.data.base.LoginResponse
import com.example.cardsnap.data.base.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {

    @POST("/auth/login")
    suspend fun login(
        @Body loginRequest : LoginRequest
    ): Response<LoginResponse>

    @POST("/auth/register")
    suspend fun register(
        @Body loginRequest : RegisterRequest
    ): Response<RegisterResponse>
}