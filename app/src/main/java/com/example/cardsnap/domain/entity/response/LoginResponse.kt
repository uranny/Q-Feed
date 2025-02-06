package com.example.cardsnap.domain.entity.response

data class LoginResponse(
    val status: Int,
    val state : String,
    val message : String,
    val data : TokenData
)
