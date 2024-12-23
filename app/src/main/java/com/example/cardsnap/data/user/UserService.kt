package com.example.cardsnap.data.user

import com.example.cardsnap.data.base.GetUserInfoResponse
import com.example.cardsnap.data.base.MyPageResponse
import com.example.cardsnap.data.base.SignupResponse
import com.example.cardsnap.data.base.UploadProfileResponse
import com.example.cardsnap.data.user.request.GetUserInfoRequest
import com.example.cardsnap.data.user.request.SignupRequest
import com.example.cardsnap.data.user.request.UploadProfileRequest
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @Multipart
    @POST("/user/upload-profile")
    suspend fun uploadProfile(
        @Header("Authorization") token: String,
        @Part image : MultipartBody.Part
    ): Response<UploadProfileResponse>

    @POST("/user/set")
    suspend fun signup(
        @Header("Authorization") token: String,
        @Body signupRequest: SignupRequest
    ): Response<SignupResponse>

    @GET("/user/{id}")
    suspend fun getUserInfo(
        @Header("Authorization") token: String,
        @Path("id") getUserInfoRequest : GetUserInfoRequest
    ): Response<GetUserInfoResponse>

    @GET("/user/me")
    suspend fun myPage(
        @Header("Authorization") token: String
    ) : Response<MyPageResponse>
}