package com.example.cardsnap.data.repository

import android.util.Log
import com.example.cardsnap.data.api.AuthService
import com.example.cardsnap.data.api.UserService
import com.example.cardsnap.domain.entity.response.GetUserInfoResponse
import com.example.cardsnap.domain.entity.request.LoginRequest
import com.example.cardsnap.domain.entity.request.RefreshRequest
import com.example.cardsnap.domain.entity.request.RegisterRequest
import com.example.cardsnap.domain.entity.response.LoginResponse
import com.example.cardsnap.domain.entity.response.MyPageResponse
import com.example.cardsnap.domain.entity.response.RefreshResponse
import com.example.cardsnap.domain.entity.response.RegisterResponse
import com.example.cardsnap.domain.entity.response.SetProfileResponse
import com.example.cardsnap.domain.entity.response.UploadProfileResponse
import com.example.cardsnap.domain.entity.request.SetProfileRequest
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RequestManager {
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

    private val authService : AuthService = retrofit.create(AuthService::class.java)
    private val userService : UserService = retrofit.create(UserService::class.java)

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

    suspend fun setRequest(token : String, signupData: SetProfileRequest) : Response<SetProfileResponse>{
        val response = userService.set(token, signupData)
        Log.d("set", "$response")
        if(!response.isSuccessful){
            throw retrofit2.HttpException(response)
        }
        return response
    }

    suspend fun getUserInfoRequest(token: String, getUserInfoData: Int) : Response<GetUserInfoResponse>{
        val response = userService.getUserInfo(token, getUserInfoData)
        Log.d("getUser", "$response")
        if(!response.isSuccessful){
            throw retrofit2.HttpException(response)
        }
        return response
    }

    suspend fun uploadProfileRequest(token : String, image : MultipartBody.Part) : Response<UploadProfileResponse>{
        val response = userService.uploadProfile(token, image)
        Log.d("uploadProfileRequest", "$response")
        if(!response.isSuccessful){
            throw retrofit2.HttpException(response)
        }
        return response
    }

    suspend fun myPageRequest(accessToken : String) : Response<MyPageResponse>{
        val response = userService.myPage(accessToken)
        Log.d("myPage", "$response")
        if(!response.isSuccessful){
            throw retrofit2.HttpException(response)
        }
        return response
    }

    suspend fun articlesRequest(accessToken: String) : Response<List<Int>>{
        val response = userService.articles(accessToken)
        Log.d("myPage", "$response")
        if (!response.isSuccessful){
            throw retrofit2.HttpException(response)
        }
        return response
    }
}