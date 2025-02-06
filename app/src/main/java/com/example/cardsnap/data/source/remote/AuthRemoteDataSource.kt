package com.example.cardsnap.data.source

import android.util.Log
import com.example.cardsnap.data.api.AuthService
import com.example.cardsnap.data.request.LoginRequest
import com.example.cardsnap.data.request.RefreshRequest
import com.example.cardsnap.data.request.RegisterRequest
import com.example.cardsnap.data.response.LoginResponse
import com.example.cardsnap.data.response.RefreshResponse
import com.example.cardsnap.data.source.base.AuthDataSource
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
     private val apiService: AuthService
) : AuthDataSource {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        val response = apiService.login(loginRequest)
        Log.d("Login", "AuthReMoteDataSource : ${response.isSuccessful} $response")
        if (!response.isSuccessful){
            throw retrofit2.HttpException(response)
        }
        return response.body()!!
    }

    override suspend fun register(registerRequest: RegisterRequest){
        val response = apiService.register(registerRequest)
        if (!response.isSuccessful){
            throw retrofit2.HttpException(response)
        }
    }

    override suspend fun refresh(refreshRequest: RefreshRequest): RefreshResponse {
        val response = apiService.refresh(refreshRequest)
        if (!response.isSuccessful){
            throw retrofit2.HttpException(response)
        }
        return response.body()!!
    }
}