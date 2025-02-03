package com.example.cardsnap.data.request

import okhttp3.MultipartBody

data class UploadProfileRequest(
    val image : MultipartBody.Part
)
