package com.example.cardsnap.domain.entity.response

data class UploadProfileResponse(
    val status: Int,
    val state : String,
    val message : String,
    val data : List<String>
)
