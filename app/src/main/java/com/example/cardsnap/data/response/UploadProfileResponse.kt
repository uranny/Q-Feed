package com.example.cardsnap.data.response

data class UploadProfileResponse(
    val status: Int,
    val state : String,
    val message : String,
    val data : List<String>
)
