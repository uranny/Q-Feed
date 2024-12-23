package com.example.cardsnap.data.auth

import android.util.Log
import com.example.cardsnap.data.auth.request.LoginRequest
import com.example.cardsnap.data.auth.request.RefreshRequest
import com.example.cardsnap.data.auth.request.RegisterRequest
import com.example.cardsnap.data.base.LoginResponse
import com.example.cardsnap.data.base.RefreshResponse
import com.example.cardsnap.data.base.RegisterResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AuthRequestManager {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://qfeed-api.euns.dev/")
        .client(
            OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val authService: AuthService = retrofit.create(AuthService::class.java)

    suspend fun loginRequest(loginData : LoginRequest): Response<LoginResponse>{
        val response = authService.login(loginData)
        Log.d("response", "$response")
        if (!response.isSuccessful){
            throw retrofit2.HttpException(response)
        }
        return response
    }

    suspend fun registerRequest(registerData : RegisterRequest) : Response<RegisterResponse>{

        val response = authService.register(registerData)
        Log.d("register", "$response")
        if (!response.isSuccessful){
            throw retrofit2.HttpException(response)
        }
        return response
    }

    suspend fun refreshRequest(refreshData: RefreshRequest) : Response<RefreshResponse>{
        val response = authService.refresh(refreshData)
        Log.d("refresh", "$response")
        if(!response.isSuccessful){
            throw retrofit2.HttpException(response)
        }
        return response
    }

}