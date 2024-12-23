package com.example.cardsnap.data.user.request

import okhttp3.MultipartBody

data class UploadProfileRequest(
    val image : MultipartBody.Part
)
