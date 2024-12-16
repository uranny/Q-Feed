package com.example.cardsnap.data.user

import android.util.Log
import com.example.cardsnap.data.base.GetUserInfoResponse
import com.example.cardsnap.data.base.MyPageResponse
import com.example.cardsnap.data.base.SignupResponse
import com.example.cardsnap.data.user.request.GetUserInfoRequest
import com.example.cardsnap.data.user.request.MyPageRequest
import com.example.cardsnap.data.user.request.SignupRequest
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object UserRequestManager {
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

    private val userService : UserService = retrofit.create(UserService::class.java)

    suspend fun signupRequest(signupData: SignupRequest) : Response<SignupResponse>{
        val response = userService.signup(signupData)
        Log.d("signup", "$response")
        if(!response.isSuccessful){
            throw retrofit2.HttpException(response)
        }
        return response
    }

    suspend fun getUserInfoRequest(getUserInfoData: GetUserInfoRequest) : Response<GetUserInfoResponse>{
        val response = userService.getUserInfo(getUserInfoData)
        Log.d("getUserInfo", "$response")
        if(!response.isSuccessful){
            throw retrofit2.HttpException(response)
        }
        return response
    }

    suspend fun myPageRequest(myPageData : MyPageRequest) : Response<MyPageResponse>{
        val response = userService.myPage()
        Log.d("myPage", "$response")
        if(!response.isSuccessful){
            throw retrofit2.HttpException(response)
        }
        return response
    }

}