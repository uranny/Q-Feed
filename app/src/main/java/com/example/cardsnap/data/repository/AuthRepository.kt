package com.example.cardsnap.data.repository

import android.util.Log
import com.example.cardsnap.domain.entity.request.LoginRequest
import com.example.cardsnap.domain.entity.request.RefreshRequest
import com.example.cardsnap.domain.entity.request.RegisterRequest
import com.example.cardsnap.domain.entity.response.TokenData
import com.example.cardsnap.data.source.base.AuthDataSource
import com.example.cardsnap.data.source.user.UserInfo
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val remoteDataSource: AuthDataSource
) {
    suspend fun login(loginRequest: LoginRequest) {
        Log.d("Login", "AuthRepository : $loginRequest")
        kotlin.run {
            val response = remoteDataSource.login(loginRequest)
            setToken(response.data)
        }
    }

    suspend fun register(registerRequest: RegisterRequest){
        kotlin.run {
            remoteDataSource.register(registerRequest)
        }
    }

    suspend fun refresh(refreshRequest: RefreshRequest) {
        kotlin.run {
            val response = remoteDataSource.refresh(refreshRequest)
            setToken(response.data)
        }
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
