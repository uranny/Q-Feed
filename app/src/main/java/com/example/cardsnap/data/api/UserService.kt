package com.example.cardsnap.data.auth

import com.example.cardsnap.data.response.GetUserInfoResponse
import com.example.cardsnap.data.response.MyPageResponse
import com.example.cardsnap.data.response.SignupResponse
import com.example.cardsnap.data.response.UploadProfileResponse
import com.example.cardsnap.data.request.SignupRequest
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface UserService {

    @Multipart
    @POST("/user/upload-profile")
    suspend fun uploadProfile(
        @Header("Authorization") token: String,
        @Part image : MultipartBody.Part
    ): Response<UploadProfileResponse>

    @POST("/user/set")
    suspend fun set(
        @Header("Authorization") token: String,
        @Body signupRequest: SignupRequest
    ): Response<SignupResponse>

    @GET("/user/{id}")
    suspend fun getUserInfo(
        @Header("Authorization") token: String,
        @Path("id") getUserInfoRequest : Int
    ): Response<GetUserInfoResponse>

    @GET("/user/me")
    suspend fun myPage(
        @Header("Authorization") token: String
    ) : Response<MyPageResponse>

    @GET("/articles")
    suspend fun articles(
        @Header("Authorization") token: String
    ) : Response<List<Int>>
}