package com.example.cardsnap.data.user

import com.example.cardsnap.data.base.GetUserInfoResponse
import com.example.cardsnap.data.base.MyPageResponse
import com.example.cardsnap.data.base.SignupResponse
import com.example.cardsnap.data.user.request.GetUserInfoRequest
import com.example.cardsnap.data.user.request.SignupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @POST("/user/signup")
    suspend fun signup(
        @Body signupRequest: SignupRequest
    ): Response<SignupResponse>

    @GET("/user/{id}")
    suspend fun getUserInfo(
        @Query("id") getUserInfoRequest : GetUserInfoRequest
    ): Response<GetUserInfoResponse>

    @GET("/user/me")
    suspend fun myPage() : Response<MyPageResponse>
}