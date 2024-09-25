package com.example.cardsnap.data.auth

import android.net.http.HttpException
import android.os.Build
import android.provider.SyncStateContract.Constants
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.annotation.RequiresExtension
import com.example.cardsnap.data.base.LoginResponse
import com.example.cardsnap.data.base.RegisterResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Tag
import java.util.concurrent.TimeUnit

object AuthRequestManager {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://ec2-43-201-132-136.ap-northeast-2.compute.amazonaws.com:8080/")
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
        if (!response.isSuccessful){
            throw retrofit2.HttpException(response)
        }
        return response
    }


}