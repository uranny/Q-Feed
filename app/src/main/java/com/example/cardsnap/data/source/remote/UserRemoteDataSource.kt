package com.example.cardsnap.data.source.remote

import android.util.Log
import com.example.cardsnap.data.api.UserService
import com.example.cardsnap.data.request.SetProfileRequest
import com.example.cardsnap.data.response.GetUserInfoResponse
import com.example.cardsnap.data.response.MyPageResponse
import com.example.cardsnap.data.response.SetProfileResponse
import com.example.cardsnap.data.response.UploadProfileResponse
import com.example.cardsnap.data.source.base.UserDataSource
import okhttp3.MultipartBody
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val apiService : UserService
) : UserDataSource {

    override suspend fun uploadProfile(token: String, image: MultipartBody.Part): UploadProfileResponse {
        val response = apiService.uploadProfile(token, image)
        Log.d("User", "UserReMoteDataSource : ${response.isSuccessful} $response")
        if (!response.isSuccessful){
            throw retrofit2.HttpException(response)
        }
        return response.body()!!
    }

    override suspend fun setProfile(token: String, setProfileRequest: SetProfileRequest): SetProfileResponse {
        val response = apiService.set(token, setProfileRequest)
        if(!response.isSuccessful){
            throw retrofit2.HttpException(response)
        }
        return response.body()!!
    }

    override suspend fun getUserInfo(token: String, userId: Int): GetUserInfoResponse {
        val response = apiService.getUserInfo(token, userId)
        if(!response.isSuccessful){
            throw retrofit2.HttpException(response)
        }
        return response.body()!!
    }

    override suspend fun myPage(token: String): MyPageResponse {
        val response = apiService.myPage(token)
        if(!response.isSuccessful){
            throw retrofit2.HttpException(response)
        }
        return response.body()!!
    }

    override suspend fun articles(token: String): List<Int> {
        val response = apiService.articles(token)
        if(!response.isSuccessful){
            throw retrofit2.HttpException(response)
        }
        return response.body()!!
    }
}