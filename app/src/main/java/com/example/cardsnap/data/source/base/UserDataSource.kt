package com.example.cardsnap.data.source.base

import com.example.cardsnap.data.request.SetProfileRequest
import com.example.cardsnap.data.response.GetUserInfoResponse
import com.example.cardsnap.data.response.MyPageResponse
import com.example.cardsnap.data.response.SetProfileResponse
import com.example.cardsnap.data.response.UploadProfileResponse
import okhttp3.MultipartBody

interface UserDataSource {
    suspend fun uploadProfile(token : String, image : MultipartBody.Part) : UploadProfileResponse
    suspend fun setProfile(token : String, setProfileRequest: SetProfileRequest) : SetProfileResponse
    suspend fun getUserInfo(token : String, userId : Int) : GetUserInfoResponse
    suspend fun myPage(token : String) : MyPageResponse
    suspend fun articles(token : String) : List<Int>
}