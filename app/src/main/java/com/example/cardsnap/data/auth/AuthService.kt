package com.example.cardsnap.data.auth

import com.example.cardsnap.data.base.LoginResponse
import com.example.cardsnap.data.base.RegisterResponse
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @POST("/auth/login")
    suspend fun login(
        @Body loginRequest : LoginRequest
    ): Response<LoginResponse>

    @POST("/auth/register")
    suspend fun register(
        @Body authRegisterRequest : RegisterRequest
    ): Response<RegisterResponse>
}