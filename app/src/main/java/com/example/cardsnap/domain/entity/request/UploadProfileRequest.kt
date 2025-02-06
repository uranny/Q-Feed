package com.example.cardsnap.domain.entity.request

import okhttp3.MultipartBody

data class UploadProfileRequest(
    val image : MultipartBody.Part
)
