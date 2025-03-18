package com.example.cardsnap.data.repository

import android.util.Log
import com.example.cardsnap.domain.entity.request.LoginRequest
import com.example.cardsnap.domain.entity.request.RefreshRequest
import com.example.cardsnap.domain.entity.request.RegisterRequest
import com.example.cardsnap.domain.entity.response.TokenData
import com.example.cardsnap.data.source.base.AuthDataSource
import com.example.cardsnap.data.source.user.UserInfo
import com.example.cardsnap.domain.entity.response.LoginResponse
import com.example.cardsnap.domain.entity.response.RefreshResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val remoteDataSource: AuthDataSource
) {
    suspend fun login(loginRequest: LoginRequest) : LoginResponse {
        Log.d("Login", "AuthRepository : $loginRequest")
        val response = remoteDataSource.login(loginRequest)
        return response
    }

    suspend fun register(registerRequest: RegisterRequest){
        kotlin.run {
            remoteDataSource.register(registerRequest)
        }
    }

    suspend fun refresh(refreshRequest: RefreshRequest) : RefreshResponse {
        val response = remoteDataSource.refresh(refreshRequest)
        return response
    }

    // Room 적용 시에 AuthLocalDataSource에서 처리 할 거
    private fun setToken(response : TokenData){
        val accessToken = response.accessToken
        val refreshToken = response.refreshToken
        val tokenType = response.tokenType

        UserInfo.accessToken = "$tokenType $accessToken"
        UserInfo.refreshToken = refreshToken
        Log.d("Login", "AuthRepository : "+  UserInfo.accessToken.toString())
    }
}
